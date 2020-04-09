# Hadoop
Details to set up Hadoop are outlined below

## About This Project


## Running The Project
1. Creating 4 instances of Ubuntu Server 18.04 LTS using Amazon EC2.
  Go to: https://us-east-2.console.aws.amazon.com/ec2/v2/home?region=us-east-2#Instances:sort=tag:Name
  Click Launch Instance
  Select Ubuntu Server 18.04 LTS (HVM)
  Choose t2.micro(Free Tier)
  Click Next to Configure Instance Details
    Number of Instances: 4
    Subnet: subnet-1d214767 | Default in us-east-2b
      Doesn’t really matter, but I want all machines in the same region
  Click Next add storage
  Click Next add Tags
    Click Add Tag
    Key: Name, Value: Hadoop
  Click Next Configure Security Group
    Use an existing security group
    Security group name: allopen
    Type: All Traffic
    Source: Anywhere
  Click Review and Launch
  Click Launch
    Select a key Pair: Hadoop
    Click check box
  Go to the instances page. Assign names to distinguish them (Hadoop-datanode1, Hadoop-datanode2, Hadoop-datanode3, Hadoop-namenode)

2. Setting up Hadoop on each individual node
  Open terminal
    chmod 400 /path/my-key-pair.pem
    ssh -i ~/Downloads/Hadoop.pem ubuntu@ec2-18-217-102-240.us-east-2.compute.amazonaws.com
    sudo su -
    passwd ubuntu
      pass (enter)
      pass (enter)
    nano /etc/ssh/sshd_config
      change PasswordAuthentication "no" to "yes"
      Change permitrootlogin to “yes”
    sudo service ssh restart
    sudo apt-get update
    sudo apt-get install default-jre
    sudo apt-get install default-jdk
    wget http://apache.mirrors.tds.net/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz
    tar zxvf hadoop-3.2.1.tar.gz
    sudo mv hadoop-3.2.1 /usr/local/hadoop
      cd /usr/local/hadoop
    sudo nano .bashrc

    <!-- /* -->
    export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop
    export HADOOP_HOME=/usr/local/hadoop
    export HADOOP_MAPRED_HOME=/usr/local/hadoop
    export HADOOP_COMMON_HOME=/usr/local/hadoop
    export HADOOP_HDFS_HOME=/usr/local/hadoop
    export YARN_HOME=/usr/local/hadoop

    # setting java path
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
    export PATH=$PATH:$JAVA_HOME/bin
    export PATH=$PATH:$HADOOP_HOME/bin
    <!-- */ -->

    source .bashrc
    <!-- # change the owner -->
    sudo chown -R ubuntu $HADOOP_HOME
    cd $HADOOP_HOME/etc/hadoop
    nano hadoop-env.sh
    <!-- update java home -->
    hadoop version
    cd ~/.ssh
    cd ~/.ssh
    ssh-keygen -t rsa
    cat id_rsa.pub >> authorized_keys
    <!-- create config file like below on master machine and copy it to the other data nodes: -->
<!--
  Host namenode
	  HostName 18.219.222.231
  	User ubuntu
    IdentityFile ~/.ssh/educate.pem
  Host datanode1
    HostName 18.217.189.60
    User ubuntu
  	IdentityFile ~/.ssh/educate.pem
  Host datanode2
  	HostName 18.222.74.251
  	User ubuntu
  	IdentityFile ~/.ssh/educate.pem
-->

<!-- copy .pem file from your machine to the master node and other data nodes, and make sure to give chmod 600 on all the pem file.  -->

<!-- ‘scp’ command: copy file from one machine to another -->

scp -r filename.pem ubuntu@IP:/home/ubuntu
<!-- Login to the machine using ip -->
mv filename.pem ~/.ssh

<!-- At this stage hadoop should be installed successfully -->
ssh namenode
ssh datanode1
<!-- Now try to do ssh namenode, ssh datanode1, ssh datanode2 from all machines, passwordless authentication should work.  -->

edit core-site.xml with the current namenode as shown below.
  copy the file to all the data nodes.
	 /usr/local/hadoop/etc/hadoop

edit hdfs-site.xml as shown below.
sudo mkdir -p /usr/local/hadoop/hadoop_data/hdfs/namenode
sudo chown -R ubuntu /usr/local/hadoop/hadoop_data/
sudo mkdir -p /usr/local/hadoop/hadoop_data/hdfs/datanode
    sudo chown -R ubuntu /usr/local/hadoop/hadoop_data/

edit yarn-site.xml as shown below.
create masters and slaves file

<!-- for example in masters file
#
namenode
# -->
<!--
in slaves file
#
datanode01
datanode02
# -->

  /usr/local/hadoop/etc/hadoop


update all the /etc/hosts file in all data nodes
<!--
#
172.31.40.54 namenode
172.31.39.100 datanode1
172.31.40.54  datanode2
# -->

NOTE: ALWAYS PRIVATE IP in HOSTS FILE, otherwise you will get an error "bind exception"
hadoop namenode -format

cd /usr/local/hadoop

$HADOOP_HOME/sbin/start-dfs.sh
$HADOOP_HOME/sbin/start-yarn.sh

jps

<!-- All set now you should see the services running on the master node and data nodes by typing in jps. -->

On master node

core-site.xml:
--------------
<property>
<name>fs.defaultFS</name>
<value>hdfs://namenode:9000</value>
</property>

hdfs-site.xml
-------------
<property>
<name>dfs.namenode.name.dir</name>
<value>file:///usr/local/hadoop/hadoop_data/hdfs/namenode</value>
</property>

yarn-site.xml
-------------
<property>
<name>yarn-nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property>
<property>
<name>yarn.nodemanager.aux-services.mapreduce_shuffle.class</name>
<value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
<property>
<name>yarn.resourcemanager.resource-tracker.address</name>
<value>namenode:8025</value>
</property>
<property>
<name>yarn.resourcemanager.scheduler.address</name>
<value>namenode:8030</value>
</property>
<property>
<name>yarn.resourcemanager.address</name>
<value>namenode:8050</value>
</property>

On data node

core-site.xml
-------------
<property>
<name>fs.defaultFS</name>
<value>hdfs://namenode:9000</value>
</property>

hdfs-site.xml
-------------
<property>
<name>dfs.datanode.data.dir</name>
<value>file:///usr/local/hadoop/hadoop_data/hdfs/datanode</value>
</property>



## Problems

If you have problems, issues, or ideas for features on this project, please add an issue to the issue tracker. As this is project is likely done being updated, there is no guarantee that the issue will be addressed
