// using java 23 with preview features enabled
void main() throws Exception{

    int[] sortedFirst;
    int[] sortedSecond;

    try(var stream = Files.lines(Path.of("input/Day1.input"))) {
        sortedFirst = stream.map(x -> x.split("\\s+")[0]).mapToInt(Integer::valueOf).sorted().toArray();
    }
    try(var stream = Files.lines(Path.of("input/Day1.input"))) {
        sortedSecond = stream.map(x -> x.split("\\s+")[1]).mapToInt(Integer::valueOf).sorted().toArray();
    }
    var result = IntStream.range(0, sortedFirst.length).map(x -> Math.abs(sortedFirst[x] - sortedSecond[x])).sum();
    println(result);

    //part2

    Map<Integer, Long> frequencyMap;
    try(var stream = Files.lines(Path.of("input/Day1.input"))) {
        frequencyMap = stream.map(x -> x.split("\\s+")[1]).mapToInt(Integer::valueOf).boxed().collect( Collectors.groupingBy(Function.identity(), Collectors.counting()) );
    }
    var result2=Arrays.stream(sortedFirst).map(x -> x*frequencyMap.getOrDefault(x, 0L).intValue()).sum();
    println(result2);
}


