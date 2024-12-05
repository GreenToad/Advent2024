void main() throws Exception{
    try(var stream = Files.lines(Path.of("input/Day2.input"))) {
        var count = stream.map(x -> x.split(" ")).map(x -> Arrays.stream(x).mapToInt(Integer::parseInt).toArray()).filter(this::isConsistent).count();
        println(count);
    }
    try(var stream = Files.lines(Path.of("input/Day2.input"))) {
        var count = stream.map(x -> x.split(" ")).map(x -> Arrays.stream(x).mapToInt(Integer::parseInt).toArray()).filter(this::isSafe).count();
        println(count);
    }
}

boolean isConsistent(int[] array){
    //is all increasing or decreasing
    boolean increasing = false;
    boolean decreasing = false;
    for(int i = 0; i < array.length-1; i++){
        if(array[i]<array[i+1]){
           increasing = true;
        }
        if(array[i]>array[i+1]){
            decreasing = true;
        }
        var diff = Math.abs(array[i]-array[i+1]);
        if(diff<1 || diff>3)
            return false;
        if(increasing && decreasing)
            return false;
    }
    return increasing ^ decreasing; //xor
}

boolean isSafe(int[] array){



    var consistent = isConsistent(array);
    if(consistent)
        return true;
    else{
        //fck it and brute force checking every possible removal
        for(int i=0;i<array.length;i++){
            int fi = i;
            var array2 = IntStream.range(0, array.length).filter(x -> x!= fi).map(x -> array[x]).toArray();
            if(isConsistent(array2))
                return true;
        }
        return false;
    }
}