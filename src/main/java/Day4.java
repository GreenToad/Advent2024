void main() throws Exception{
    char[][] input = Files.readAllLines(Path.of("input/Day4.input" )).stream().map(String::toCharArray).toArray(char[][]::new);
    //unfortunately first index in array is row number and second is column so its input[y][x]

    //find 'X'
    int sum = 0;
    for(int y = 0; y < input.length; y++){
        for(int x = 0; x < input[y].length; x++){
            //print(input[y][x]);
            if(input[y][x] == 'X'){
                sum+=check(input, x,y);

            }
        }
    }
    println(sum);

    //part2
    //find 'A'
    int sum2 = 0;
    for(int y = 0; y < input.length; y++){
        for(int x = 0; x < input[y].length; x++){
            //print(input[y][x]);
            if(input[y][x] == 'A'){
                sum2+=check2(input, x,y);
            }
        }
    }
    println(sum2);

}

private int check(char[][] input, int x, int y) {
    //go clockwise
    var expected = new char[]{'M','A','S'}; //checking for 'X' at start not necessary
    int sum = 0;
    if(x<input[y].length-3){
        //checkEast
        sum+=Arrays.equals(new char[]{input[y][x+1],input[y][x+2],input[y][x+3]}, expected)?1:0;
        if(y>2){
            //checkNorthEast
            sum+=Arrays.equals(new char[]{input[y-1][x+1],input[y-2][x+2],input[y-3][x+3]}, expected)?1:0;
        }
        if(y<input.length-3){
            //checkSouthEast
            sum+=Arrays.equals(new char[]{input[y+1][x+1],input[y+2][x+2],input[y+3][x+3]}, expected)?1:0;
        }
    }
    if(x>2){
        //checkWest
        sum+=Arrays.equals(new char[]{input[y][x-1],input[y][x-2],input[y][x-3]}, expected)?1:0;
        if(y>2){
            //checkNorthWest
            sum+=Arrays.equals(new char[]{input[y-1][x-1],input[y-2][x-2],input[y-3][x-3]}, expected)?1:0;
        }
        if(y<input.length-3){
            //checkSouthWest
            sum+=Arrays.equals(new char[]{input[y+1][x-1],input[y+2][x-2],input[y+3][x-3]}, expected)?1:0;
        }
    }

    if(y>2){
        //checkNorth
        sum+=Arrays.equals(new char[]{input[y-1][x],input[y-2][x],input[y-3][x]}, expected)?1:0;
    }
    if(y<input.length-3){
        //checkSouth
        sum+=Arrays.equals(new char[]{input[y+1][x],input[y+2][x],input[y+3][x]}, expected)?1:0;
    }
    return sum;
}

private int check2(char[][] input, int x, int y) {

    var expected = new char[]{'M','S'}; //checking for 'A' in middle not necessary
    var expected2 = new char[]{'S','M'};
    boolean foundNWSE = false;
    boolean foundSWNE = false;
    if(x<input[y].length-1 && x>0){
        if(y>0 && y<input.length-1){
            //checkNorthWestSouthEast
            var nwse = new char[]{input[y-1][x-1],input[y+1][x+1]};
            var swne = new char[]{input[y+1][x-1],input[y-1][x+1]};
            foundNWSE = Arrays.equals(nwse, expected) || Arrays.equals(nwse, expected2);
            foundSWNE = Arrays.equals(swne, expected) || Arrays.equals(swne, expected2);
        }
    }
    return (foundNWSE && foundSWNE)?1:0;
}