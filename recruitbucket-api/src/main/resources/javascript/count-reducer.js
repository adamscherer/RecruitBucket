function Reduce(key, values) {

    var reduced = {
        count : 0
    };

    values.forEach(function(val) {
        reduced.count += val.count;
    });

    return reduced;

}