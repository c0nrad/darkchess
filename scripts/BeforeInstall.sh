if [ -d /opt/darkchess/client ]; then
    rm -rf /opt/darkchess/client
fi

if [ -f /opt/darkchess/*.jar ]; then 
    rm /opt/darkchess/*.jar
fi
