void main() throws Exception{
    //as usual input like that is [y][x]
    char[][] inputTransposed = Files.readAllLines(Path.of("input/Day6.input" )).stream().map(String::toCharArray).toArray(char[][]::new);
    //lets try and have fun with addressing it [x][y] to make it more sane
    char[][] map = new char[inputTransposed[0].length][inputTransposed.length];

    for (int i = 0; i < inputTransposed.length; i++)
        for (int j = 0; j < inputTransposed[i].length; j++)
            map[j][i] = inputTransposed[i][j];

    char[][] visited = new char[map.length][map[0].length];

    int guardx=0;
    int guardy=0;
    var guardFacing=Direction.UP;
    //find first position
    out: for(int x = 0; x < map.length; x++){
        for(int y = 0; y < map[x].length; y++){
            if(map[x][y] != '.' && map[x][y] != '#'){
                guardFacing=Direction.parse(map[x][y]);
                guardx=x;
                guardy=y;
                visited[x][y]='X';
                break out;
            }
        }
    }

    while(true){
        var newx=guardx+guardFacing.dx;
        var newy=guardy+guardFacing.dy;
        if(newx==map.length || newx==-1 || newy==map[0].length || newy==-1){
            break; // guard exited
        }
        if(map[newx][newy] == '#'){
            guardFacing=guardFacing.rotate();
        }else{
            visited[newx][newy]='X';
            guardx=newx;
            guardy=newy;
        }
    }


    //unfortunately when we have arrays indexed with [x][y] we need to loop with first loop on y and second on x to pring
    /*for(int y=0; y < map[0].length; y++){
        for(int x=0; x < map.length; x++){
            print(map[x][y]);
        }
        println("");
    }
    println("test");

    for(int y=0; y < map[0].length; y++){
        for(int x=0; x < map.length; x++){
            print(visited[x][y]);
        }
        println("");
    }
    println("test");
    */

    //count x in visited
    int count=0;
    for(int y=0; y < map[0].length; y++){
        for(int x=0; x < map.length; x++){
            if(visited[x][y] == 'X')
                count++;
        }
    }
    println(count);

    //so for part 2 we need to record in our path not only if "visited" but also "visited facing directions [..]"
    //when we visit same space with same direction that means guard is in a loop
    //so we simulate guard movement again this time recording directions of visit
    //but before simulation try placing an obstacle on a space of the original guard path and check if we hit a loop
    //original "visited" 2d array is gives is obstacle candidate for every X (except starting point)
    //so we need to run simulation as many times as answer to part 1 -1 (my case 4373) and checking if we exit or hit a loop


    byte[][] visitedDirection = new byte[map.length][map[0].length];

    //lets find the guard again
    //clear his starting position from visited 2d array
    //run simulation again

}

enum Direction {
    UP(0,-1, (byte) (1<<0)) {
        @Override
        Direction rotate() {
            return RIGHT;
        }
    },
    RIGHT(1,0, (byte) (1<<1)) {
        @Override
        Direction rotate() {
            return DOWN;
        }
    },
    DOWN(0,1, (byte) (1<<2)) {
        @Override
        Direction rotate() {
            return LEFT;
        }
    },
    LEFT(-1,0,(byte)(1<<3)) {
        @Override
        Direction rotate() {
            return UP;
        }
    };

    public final int dx;
    public final int dy;
    public final int mask;

    Direction(int dx, int dy, byte mask) {
        this.dx = dx;
        this.dy = dy;
        this.mask = mask;
    }

    public static Direction parse(char c){
        return switch(c){
            case 'v' -> DOWN;
            case '^' -> UP;
            case '<' -> LEFT;
            default -> RIGHT;
        };
    }

    abstract Direction rotate();

}