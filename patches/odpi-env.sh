COMMANDS="hadoop hdfs mapred yarn"

for COMMAND in $COMMANDS; do
    for LINE in $($COMMAND envvars); do
        # Remove quotes ...
        LINE=$(echo $LINE | sed "s/'//g")
        export $LINE
    done
done
