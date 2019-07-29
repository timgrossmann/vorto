# Create a full payload mapping pipeline

This tutorial explains how to create a full pipeline for payload mapping with a real device sending device payload.

In this example we'll be using a pre-created model you can find in the [Vorto Repository](.). Of course, you can also use your own device.    
Throughout this tutorial, we will set up a pipeline of mapping device payload that is then sent to AWS AMQ in order to allow easy subscription of applications.

![payload mapping schema](https://cdn-images-1.medium.com/max/2560/1*DvMdmWE11eTa_ZlvtjJ3cw.png)

In our case, we send csv data from our device, a Permanent Magnet Synchronous Motor or short PMSM, to Bosch IoT Hub via MQTT. We use the message broker of Hub to provide a subscribable endpoint from which we can get the binary data from.   

Our Mapper is a Java application that consumes the data from that AMQP topic and pipes it through the Mapping Engine. The engine is running on the same machine as the Mapper in this case.
Once mapped, the Mapper will send the, now normalized, Vorto compliant payload to AWS AMQ which provides it to be consumed by different applications.

We will connect Bosch IoT Things with AMQ to consume the data and update our Digital Twin in Things. This will then allow us to use the Vorto Dashboard in order to display our device data with predefined UI widgets.


<br />

### Prerequisite
* [BoschID](https://accounts.bosch-iot-suite.com/) Account or [GitHub](https://github.com/) 
* You are a collaborator/owner of a namespace
* Subscription to [Asset Communication for Bosch IoT Suite](https://www.bosch-iot-suite.com/asset-communication/) (Free plan, no credit card required)
* AWS Account for [AMQ Service](https://aws.amazon.com/amazon-mq/)

<br />

## Steps

1. Register your device and create a new Thing from the Information Model.   
2. Send data from the device to [IoT Hub](https://www.bosch-iot-suite.com/service/hub/).    
2. Receive data from Hub with the Data Normalizer   
3. Create a Mapping Specification for your device.   
4. Set up AWS AMQ to provide your device data   
5. Connect Things to your AMQ Instance   
6. Visualize your device data with the [Vorto Dashboard](https://github.com/eclipse/vorto-examples/tree/master/vorto-dashboard)

<br />

## Register your device and create a new Thing from the Information Model.
... TODO

Since the telemetry data content type of devices is not standardized, we need to make sure that we have it defined inside the device registry in order to let the mapping engine know which format our payload is in.
Using Eclipse Hono, we can add this information, together with the definition of our Vorto Information Model, to the `defaults` attribute of our device registration. In our case, we'll use the Bosch IoT Hub device registry.

Our device is sending its state in the form of comma separated values, this means we need to register our device with the content-type of text/csv.

```csv
63,1563866900,0.6614805,2.175339,-1.2224298,-0.25563973,1.6673933,1.1077263,0.59097534
```

The `vorto` attribute will tell our Mapping Engine which Mapping Specification to choose.
Therefore our device registration settings will look like this.

```json
{
  "enabled": true,
  "defaults": {
    "content-type": "text/csv",
    "vorto": "vorto.private.timgrossmann:PMSMMotor:2.0.0"
  },
  "device-id": "pmsm0815"
}
```

Once we have registered our device with Hub, we can send data from our device to Hub.

<br />

## Send data from the device to [IoT Hub](https://www.bosch-iot-suite.com/service/hub/)
We will not go into detail on how to do the integration of this specific device here since this is always dependant on the device itself.   
However feel free to read through and use one of the tutorials for:
- [Python](mqtt-python.md)
- [Java](connect_javadevice.md)
- [Arduino](connect_esp8266.md)

<br />

## Create a Mapping Specification for your device.
The Mapping Editor in the Vorto Repository provides a convenient way to create and test mappings. Every Function Block gets its own mapping, this means full control over what and how should be mapped.
When taking a look at the possibilities in the mapping editor, we can see 2 predominant elements, `Condition` and `Status Properties`.

![mapping editor](https://cdn-images-1.medium.com/max/800/1*cr5vwdToFhVlYu3K8sLKeg.png)

As mentioned in the example overview, we are using CSV data here that will be deserialized into an array of Strings.
Using this knowledge, we can get the according elements of the array by indexing them, with the indices starting at 1 instead of 0, and then converting them to the data types we are expecting.

Array is a keyword here that will reference the input String array element that comes out of simply splitting the CSV at comma.
We can make sure that we only map specific elements by providing a condition like the one we used above to avoid mapping non provided values.

Once we finished specifying how our mapping should be done, we can download it and copy it into our `specs` folder in the Mapping Engine.

<br />

## Receive data from Hub with the Data Normalizer
After running the mapping engine and sending data from our device, the resulting output in the console of our mapping looks like this.

```json
{
  "profile_id": {
    "status": {
      "id": 63
    }
  },
  "ambient": {
    "status": {
      "unit": "C",
      "value": 0.6614805
    }
  },
  "timestamp": {
    "status": {
      "timestamp": 1563866900
    }
  }
}
```

The resulting Normalized Vorto Payload maps the given values from our CSV to the according Function Blocks in a simple JSON format.   
This normalized payload can now again be mapped into Vendor specific formats, like in our case the Eclipse Ditto format used with Bosch IoT Things.

<br />

## Set up AWS AMQ to provide your device data

<br />

## Connect Things to your AMQ Instance

<br />

## Visualize your device data with the [Vorto Dashboard](https://github.com/eclipse/vorto-examples/tree/master/vorto-dashboard)

---

In case you're having difficulties or facing any issues, feel free to [create a new question on StackOverflow](https://stackoverflow.com/questions/ask) and we'll answer it as soon as possible!   
Please make sure to use `eclipse-vorto` as one of the tags. 
