/**
 * 
 */
package com.hpe.iot.mqtt.sensenuts.lighting;

import static com.hpe.iot.mqtt.sensenuts.lighting.SensenutsStreetLightingTestData.DEVICE_ID;
import static com.hpe.iot.mqtt.test.constants.TestConstants.SENSENUTS;
import static com.hpe.iot.mqtt.test.constants.TestConstants.SENSENUTS_MODEL;
import static com.hpe.iot.mqtt.test.constants.TestConstants.SENSENUTS_VERSION;
import static com.hpe.iot.utility.UtilityLogger.logRawDataInHexaDecimalFormat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hpe.iot.model.DeviceInfo;
import com.hpe.iot.mqtt.southbound.service.inflow.ReceivedMqttMessage;
import com.hpe.iot.mqtt.test.base.MqttBaseTestTemplate;
import com.hpe.iot.utility.UtilityLogger;

/**
 * @author sveera
 *
 */
public class SensenutsStreetLightingTest extends MqttBaseTestTemplate {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final SensenutsStreetLightingTestData sensenutsStreetLightingTestData = new SensenutsStreetLightingTestData();

	@Test
	@DisplayName("test Sensenuts Street Lighting For Uplink Notification")
	public void testSensenutsStreetLightingForUplinkNotification() throws InterruptedException {
		logRawDataInHexaDecimalFormat(sensenutsStreetLightingTestData.constructPeriodicMessage(), getClass());
		tryPublishingMessage(formUplinkTopicName(SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID),
				sensenutsStreetLightingTestData.constructPeriodicMessage());
		DeviceInfo deviceInfo = iotDevicePayloadHolder.getIOTDeviceData();
		assertNotNull(deviceInfo, "Device info cannot be null");
		validateDeviceModel(deviceInfo.getDevice(), SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID);
		logger.debug("Received Device Info is " + deviceInfo);
	}

	@Test
	@DisplayName("test Sensenuts Street Lighting For Uplink Power Outage Alert")
	public void testSensenutsStreetLightingForUplinkPowerOutageAlert() throws InterruptedException {
		logRawDataInHexaDecimalFormat(sensenutsStreetLightingTestData.constructPowerOutageAlertMessage(), getClass());
		tryPublishingMessage(formUplinkTopicName(SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID),
				sensenutsStreetLightingTestData.constructPowerOutageAlertMessage());
		DeviceInfo deviceInfo = iotDevicePayloadHolder.getIOTDeviceData();
		assertNotNull(deviceInfo, "Device info cannot be null");
		validateDeviceModel(deviceInfo.getDevice(), SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID);
		logger.debug("Received Device Info is " + deviceInfo);
	}

	@Test
	@DisplayName("test Sensenuts Street Lighting For Uplink Luminaire Failure Alert")
	public void testSensenutsStreetLightingForUplinkLuminaireFailureAlert() throws InterruptedException {
		logRawDataInHexaDecimalFormat(sensenutsStreetLightingTestData.constructLuminaireFailureMessage(), getClass());
		tryPublishingMessage(formUplinkTopicName(SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID),
				sensenutsStreetLightingTestData.constructLuminaireFailureMessage());
		DeviceInfo deviceInfo = iotDevicePayloadHolder.getIOTDeviceData();
		assertNotNull(deviceInfo, "Device info cannot be null");
		validateDeviceModel(deviceInfo.getDevice(), SENSENUTS, SENSENUTS_MODEL, SENSENUTS_VERSION, DEVICE_ID);
		logger.debug("Received Device Info is " + deviceInfo);
	}

	@Test
	@DisplayName("test Sensenuts Street Lighting Change Brightness Of Light Downlink Command")
	public void testSensenutsStreetLightingChangeBrightnessOfLight_DownlinkCommand() throws InterruptedException {
		mockNorthboundDownlinkProducerService
				.publishDownlinkData(createNorthboundOneM2MDownlinkCommandForSensenutsChangeBrightness());
		waitForDCToCompletePayloadProcessing();
		ReceivedMqttMessage downlinkCommand = mqttDevicePayloadHolder.getMqttDeviceData();
		logger.trace("Expected downlinkCommand bytes are " + UtilityLogger.convertArrayOfByteToString(
				sensenutsStreetLightingTestData.constructChangeLightBrightnessDownlinkCommandMessage()));
		logger.trace("Actual downlinkCommand bytes are "
				+ UtilityLogger.convertArrayOfByteToString(downlinkCommand.getMqttMessage()));
		assertArrayEquals(sensenutsStreetLightingTestData.constructChangeLightBrightnessDownlinkCommandMessage(),
				downlinkCommand.getMqttMessage(), "Expected and actual downlink commands are not same");
	}

	private String createNorthboundOneM2MDownlinkCommandForSensenutsChangeBrightness() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><?xml-oneM2m oneM2M=\"1.10\"?><ns2:requestPrimitive xmlns:ns2=\"http://www.onem2m.org/xml/protocols\">"
				+ "<operation>5</operation><to>HPE_IoT/Light-1234</to><from>/CSE1000</from><requestIdentifier>45f440d7b169453f8e5f00e15046444b</requestIdentifier><primitiveContent><ns2:notification><notificationEvent><representation xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "{\"m2m:cin\":{\"ty\":4,\"ri\":\"HPE_IoT/Light-1234/downlinkCommand/8969087f43c\",\"pi\":\"HPE_IoT/Light-1234/downlinkCommand\",\"ct\":\"20171009T084338,000232\",\"lt\":\"20171009T084338,000232\",\"rn\":\"8969087f43c\",\"et\":\"20271007T084338,000220\",\"st\":1,\"cr\":\"C070878C6-85f18dc6\",\"cnf\":\"text/plain:0\",\"cs\":352,"
				+ "\"con\":\"{\\\"device\\\":{\\\"manufacturer\\\":\\\"sensenuts\\\",\\\"modelId\\\":\\\"lighting\\\",\\\"version\\\":\\\"1.0\\\",\\\"deviceId\\\":\\\"1234\\\"},\\\"messageType\\\":\\\"downlinkCommand\\\",\\\"payload\\\":{\\\"gatewayId\\\":\\\"1234\\\",\\\"lightId\\\":\\\"0001\\\",\\\"brightness\\\":\\\"50\\\"}}\"}}</representation><operationMonitor>"
				+ "<operation>5</operation><originator>C070878C6-c4c0c7c1</originator></operationMonitor><notificationEventType>1</notificationEventType></notificationEvent><verificationRequest>false</verificationRequest><subscriptionReference>7524845195623549720</subscriptionReference><creator>C070878C6-c4c0c7c1</creator></ns2:notification>"
				+ "</primitiveContent><responseType><responseTypeValue>2</responseTypeValue></responseType></ns2:requestPrimitive>";
	}

}
