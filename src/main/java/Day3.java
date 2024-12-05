import static java.lang.Long.parseLong;

void main() throws Exception {
    Thread.sleep(1000);
    try (var stream = Files.lines(Path.of("input/Day3.input"))) {
        var mul = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        var result = stream.mapToLong(x -> mul.matcher(x).results().mapToLong(r -> parseLong(r.group(1)) * parseLong(r.group(2))).sum()).sum();
        println(result);
    }

    try (var stream = Files.lines(Path.of("input/Day3.input"))) {
        // argh! so we shouldn't treat it line by line!
        // oh well just join the lines
        var full=cleanup(stream.collect(Collectors.joining("")));
        var mul = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        var result = mul.matcher(full).results().mapToLong(r -> parseLong(r.group(1)) * parseLong(r.group(2))).sum();
        println(result);
    }
}

// proper solution - regex finding all 3 options mul/do/dont and sequentialy do different things based on what you find
// but being lazy decided to recursively remove everything between don't() end do()
// hoping not to blow the stack
private String cleanup(String s){

    var dni = s.indexOf("don't()");
    if(dni>=0){
        var di = s.indexOf("do()", dni+7);
        if(di>=0){
            return cleanup(s.substring(0,dni)+s.substring(di+4));
        }
        return s.substring(0,dni);
    }
    return s;
}