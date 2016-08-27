
######### Config ####################
TMP_DIR=/tmp/jenkins-update
JENKIN_CLI=$TMP_DIR/jenkins-cli.jar 
JENKIN_URL=http://localhost:10000

###### Restart the Jenkins server before any update##############
/etc/init.d/jenkins restart

while [ $(curl -o /dev/null --silent --head --write-out '%{http_code}\n' $JENKIN_URL) != "200" ]; do
  sleep 2 
  echo "Waitig for Jenkins to boot up . . . ."
done

mkdir -p $TMP_DIR
wget -P $TMP_DIR $JENKIN_URL/jnlpJars/jenkins-cli.jar

## Update Jenkins ##
UPDATE_LIST=$( java -jar $JENKIN_CLI -s $JENKIN_URL list-plugins | grep -e ')$' | awk '{ print $1 }' ); 
if [ ! -z "${UPDATE_LIST}" ]; then 
    echo Updating Jenkins Plugins: ${UPDATE_LIST}; 
    java -jar $JENKIN_CLI -s $JENKIN_URL install-plugin ${UPDATE_LIST};
    java -jar $JENKIN_CLI -s $JENKIN_URL safe-restart;
fi


java -jar $JENKIN_CLI -s $JENKIN_URL install-plugin FSTrigger GitHub

###### Restart the Jenkins server before any update##############
/etc/init.d/jenkins restart

sudo rm -r $TMP_DIR
