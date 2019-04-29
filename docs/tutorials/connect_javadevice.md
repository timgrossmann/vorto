# Integrating a Java - based Device with the Bosch IoT Suite using Vorto

This tutorial explains how Vorto is used to create a simple Java application that sends device telemetry data to the Bosch IoT Suite.

This tutorial explains how to integrate a device with the Bosch IoT Suite using Java and Maven. Your device should already be created as a thing from an Information Model at this point.   
We will use this [Distance Sensor VIM](https://vorto.eclipse.org/#/details/org.eclipse.vorto.tutorial:DistanceSensor:1.0.0).

<img src="../images/tutorials/connect_grovepi/cover.png"/>

## Prerequisites

* A [Java Runtime](https://www.java.com/en/download/) and [Maven](https://maven.apache.org/index.html) are installed

* Some code editor is installed (e.g. [Eclipse IDE](https://www.eclipse.org/ide/))

* Created a thing in the Bosch IoT Suite (refer to [Creating a Thing in the Bosch IoT Suite](create_thing.md)).

<br />

## Steps
1. Setup your device
1. Download the generated integration script
1. Configure the scripts with the information of your created thing
1. Reading the sensor data
1. Start sending data

<br />

## Download the generated integration script

**1.** On the Vorto Repository page of your Information Model (we will use this [Distance Sensor](https://vorto.eclipse.org/#/details/org.eclipse.vorto.tutorial:DistanceSensor:1.0.0)), click on the `Bosch IoT Suite` generator. This will trigger a pop up to appear with the available generators.     
<img src="../images/tutorials/create_thing/code_generators.png" />

**2.** We want to integrate a device using Java. Click the `Source Code` button to download the generated Java project.

<img src="../images/tutorials/connect_grovepi/python-generator.png" height="500"/>

**3.** Unzip the downloaded file and open it in your Eclipse IDE. 

**4.** In order to guarantee secure transmission of your data, the integration uses SSL. We therefore need a certificate.   
Right click and save the [iothub.crt](https://docs.bosch-iot-hub.com/cert/iothub.crt) file and place it at `<project>/src/main/resources/certificates/hono.crt` as `hono.crt`.	

<br />
		
3. Generate and Download a Java Source Code Template for the device.
	
	- Go back to the [Vorto Console](https://vorto.eclipse.org/console).

	- Navigate to your thing in the Thing Browser and click on it.

	- Click on the **Source Code Templates** tab.

	- At the **Integrate device with Java** template, click **Download**.

		<img width="300" src="../images/tutorials/connect_java/code_template.png" style="border:3px !important;">

	- Store the ZIP file and extract the source code.

4. Import the source code bundle as a Maven project into your IDE.

<br />

## Configure the scripts with the information of your created thing

5. Configure the endpoint in the generated source code.

	- Open the class **DistanceSensorApp**.

	- Set the **Password**, you have specified during the thing creation process (refer to above).
	
	- Set the **AUTH_ID** to the format like **Technical Device ID@HONO_TENANT**, e.g. 471100@t932f4726de33232223121_hub
	
	- Set the **DITTO_TOPIC** to **Things Namespace/Technical Device ID**. (e.g org.mycompany/4711)

<br />

## Start sending data

6. Run and verify incoming sensor data.

	- Right-Click on the **DistanceSensorApp.java**. Choose **Run As** and select **Java Application**. 
	
	> The running application prints out the logs to the IDE console.

	- Go back to the [Vorto Console](https://vorto.eclipse.org/console).

	- Navigate to your thing in the Thing Browser and click on it.

	- Click on Refresh to fetch the latest digital twin data

	- Check if the sensor data was sent successfully to the Bosch IoT Suite.

		<img width="500" src="../images/tutorials/connect_java/verifydata.png" style="border:3px !important;">


**Great!** You just sent device payload complying to the Bosch IoT Suite using Vorto. Feel free to create another Vorto Information Model for your device.
 
<br />

## What's next ?

 - [Use the Vorto Dashboard](create_webapp_dashboard.md) to visualize the device data in UI widgets.
