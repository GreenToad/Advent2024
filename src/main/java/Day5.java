void main() throws Exception{
    var lines = Files.readAllLines(Paths.get("input/Day5.input"));
    var rules= lines.stream().takeWhile(x -> Objects.equals(x,"")==false).map(Rule::new).toList();
    // map with befores and afters for each value
    Map<Integer, Rules> combinedRules= new HashMap<>();
    rules.forEach(x ->{
        combinedRules.computeIfAbsent(x.before, _ -> new Rules()).after().add(x.after());
        combinedRules.computeIfAbsent(x.after, _ -> new Rules()).before().add(x.before());
    });

    var testSets = lines.stream().dropWhile(x ->Objects.equals(x,"")==false).skip(1).map(x -> Arrays.stream(x.split(",")).mapToInt(Integer::parseInt).toArray()).toList();

    var result=0;
    for (var set:testSets){
        result+=testRuleMatch(set, combinedRules);
    }
    println(result);

    //find wrong sets
    var wrong = testSets.stream().filter(x -> testRuleMatch(x, combinedRules)==0).toList();

    var result2=0;
    for (var set:wrong){
        result2+=fixSet(set, combinedRules);
    }
    println(result2);



}

private static int testRuleMatch(int[] set, Map<Integer, Rules> combinedRules) {
    for (int i = 0; i < set.length; i++) {
        var element = set[i];
        var elementRules= combinedRules.get(element);
        if(i>0){
            //things before cannot be in "after" combined rules
            if(Arrays.stream(set, 0, i).anyMatch(x -> elementRules.after().contains(x))){
                //wrong
                return 0;
            }
        }
        if(i< set.length-1){
            //things after cannot be in "before" combined rules
            if(Arrays.stream(set, i+1, set.length).anyMatch(x -> elementRules.before().contains(x))){
                //wrong
                return 0;
            }
        }
    }
    return set[set.length/2];
}


// Im not going to try to use a comparator because i suspect that rules are not transitive - if a is before b and b is before c that doesn't mean that a is before c, maybe they are maybe they are not, assuming not
// ok so in every set of numbers we find at least one that has no other numbers from set in its "before" rules, this will be the first element
// and we repeat this until we sorted all numbers
private static int fixSet(int[] set, Map<Integer, Rules> combinedRules){
    var result = new ArrayList<Integer>();
    var mutSet= Arrays.stream(set).boxed().collect(Collectors.toList());

    //fuck hope this works
    //holy shit it worked!
    while(mutSet.size()>0) {
        var found = mutSet.stream().filter(x -> {
            var beforeRules = combinedRules.get(x).before();
            //if no others elements in before we add this to result
            return mutSet.stream().filter(y -> x.equals(y) == false).allMatch(y -> beforeRules.contains(y) == false);
        }).toList();

        mutSet.removeAll(found);
        result.addAll(found);
    }
    return result.get(result.size()/2);
}

record Rule(int before, int after){
    Rule(String s){
        var sa=s.split("\\|");
        this(Integer.parseInt(sa[0]),Integer.parseInt(sa[1]));
    }
}
record Rules(HashSet<Integer> before, HashSet<Integer> after){
    Rules(){
        this(new HashSet<>(), new HashSet<>());
    }
}