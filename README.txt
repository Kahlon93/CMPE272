
          The MiniTwitter application is a replication of the modern day Twitter application in that it allows a user to view messages (tweets), add friends to a group, view the different friend conversations and allow real-time communication updates. The application makes use of parallel-processing and runs on a separate thread to simulate Instant Messaging technologies. The application can be used by multiple users at the same time so there is a high risk of over utilizing computing resources. This is handled by creating memory and disk alerts through implemented scripts by using a Kapacitor, which in turn uses a Telegraf configuration and an InfluxDB to provide CPU alerts.  A Kapacitor is a data processing engine that has the capability of processing both stream and batch data. This application utilizes the features of InfluxDb and a Telegraf configuration along with scripts generated through Kapacitor to trigger CPU alerts for when the CPU is being above a certain specified threshold (70%) 


CONFIGURATION

To run this application the tools that you need to install are as follows:

1.	Java Runtime environment ( Java SE 8)
2.	Kapacitor
3.	InfluxDB
4.	Telegraf

To start the InfluxDB you can type the command: 

influx run

This will generate a Telegraf configuration file to communicate with the InfluxDB. The file is saved under ‘telegraf.conf’ in the project directory.  Then to start the Telegraf you need to type the following command in the terminal: 

telegraf –config telegraf.conf

This will run the InfluxDB along with the Telegraf setup. To see if there are any pre-defined cpu metrics in the database you can query it through the command line by typing the query along with your localhost URL. For example, if you wanted to query the number of idle usage of the CPU you can type the following command: 

    curl -G 'http://localhost:8000/query?db=kapacitor_example' --data-urlencode 'q=SELECT count(usage_idle) FROM cpu'

The next step is to start the Kapacitor engine. To generate a configuration file for the kapacitor you can type the following command in to your command line: 

Kapacitord –config kapacirot.conf

This command starts the kapacitor and links it to the InfluxDB, which for this application is running under http://localhost:8000, and tells the InfluxDB to send and receive all data to this Kapacitor. 


CPU ALERT IMPLEMENATION

          To generate the CPU alerts for our application, we define a file called ‘cpu_alert.tick’ and place it in the project directory. The Kapacitor uses a DSL called TICKscript which is used to define tasks and a pipline that informs the Kapacitor on how to process it’s data. For our application we defined our TICKscript to trigger an alert when the idle CPU drops below 65%. The Kapacitor uses an HTTP API wit which all of it’s communications happen therefore it is possible to generate commands to the Kapacitor with the command line while the application is running.

MEMORY ALERT IMPLEMENTATION

          The TICKscript for the memory alert is defined in a file named ‘disk_alert.tick’ and is placed in the project directory. Alogside with creating the CPU alerts and the memory alerts we must define the task, database, and retention policies so that the Kapacitor knows how to trigger the alerts. This can be accomplished by the following command: 

kapacitor record stream -task cpu_alert -duration 20s

Note that the cpu_alert is our file for creating cpu alerts and the same should be done with the memory alert file (disk_alert) as well. This will generate a report every 20s the application runs. 



