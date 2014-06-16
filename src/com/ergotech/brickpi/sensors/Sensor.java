/*
 *  Copyright ErgoTech Systems, Inc 2014
 *
 * This file is made available online through a Creative Commons Attribution-ShareAlike 3.0  license.
 * (http://creativecommons.org/licenses/by-sa/3.0/)
 *
 *  This is a library of functions for the RPi to communicate with the BrickPi.
 */
package com.ergotech.brickpi.sensors;

import java.util.BitSet;

/**
 *
 * The class representing a sensor.
 */
public abstract class Sensor {

    public static final byte MASK_D0_M = 0x01;
    public static final byte MASK_D0_S = 0x08;

    public static final byte TYPE_SENSOR_RAW = 0;
    public static final byte TYPE_SENSOR_LIGHT_OFF = 0;
    public static final byte TYPE_SENSOR_LIGHT_ON = (MASK_D0_M | MASK_D0_S);
    public static final byte TYPE_SENSOR_TOUCH = 32;
    public static final byte TYPE_SENSOR_ULTRASONIC_CONT = 33;
    public static final byte TYPE_SENSOR_ULTRASONIC_SS = 34;
    public static final byte TYPE_SENSOR_RCX_LIGHT = 35;
    public static final byte TYPE_SENSOR_COLOR_FULL = 36;
    public static final byte TYPE_SENSOR_COLOR_RED = 37;
    public static final byte TYPE_SENSOR_COLOR_GREEN = 38;
    public static final byte TYPE_SENSOR_COLOR_BLUE = 39;
    public static final byte TYPE_SENSOR_COLOR_NONE = 40;
    public static final byte TYPE_SENSOR_I2C = 41;
    public static final byte TYPE_SENSOR_I2C_9V = 42;
    
    public static final byte TYPE_SENSOR_EV3_US_M0        = 43;
    public static final byte TYPE_SENSOR_EV3_US_M1        = 44;
    public static final byte TYPE_SENSOR_EV3_US_M2        = 45;
    public static final byte TYPE_SENSOR_EV3_US_M3        = 46;
    public static final byte TYPE_SENSOR_EV3_US_M4        = 47;
    public static final byte TYPE_SENSOR_EV3_US_M5        = 48;
    public static final byte TYPE_SENSOR_EV3_US_M6        = 49;
    public static final byte TYPE_SENSOR_EV3_COLOR_M0     = 50;
    public static final byte TYPE_SENSOR_EV3_COLOR_M1     = 51;
    public static final byte TYPE_SENSOR_EV3_COLOR_M2     = 52;
    public static final byte TYPE_SENSOR_EV3_COLOR_M3     = 53;
    public static final byte TYPE_SENSOR_EV3_COLOR_M4     = 54;
    public static final byte TYPE_SENSOR_EV3_COLOR_M5     = 55;
    public static final byte TYPE_SENSOR_EV3_GYRO_M0      = 56;
    public static final byte TYPE_SENSOR_EV3_GYRO_M1      = 57;
    public static final byte TYPE_SENSOR_EV3_GYRO_M2      = 58;
    public static final byte TYPE_SENSOR_EV3_GYRO_M3      = 59;
    public static final byte TYPE_SENSOR_EV3_GYRO_M4      = 60;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M0  = 61;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M1  = 62;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M2  = 63;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M3  = 64;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M4  = 65;
    public static final byte TYPE_SENSOR_EV3_INFRARED_M5  = 66;

    /**
     * The current value of the sensor.
     */
    protected int value;

    /**
     * Returns the type of this sensor.
     */
    public abstract byte getSensorType();

    /**
     * Encode the data associated with the sensor to the outgoing message. The
     * default method does nothing.
     *
     * @param message the BitSet representing the outgoing message.
     * @param startLocation the starting bit location in the message at which to
     * begin encoding
     * @return the ending location. That is the startLocation for the next
     * encoding.
     */
    public int encodeToSetup(BitSet message, int startLocation) {
        return startLocation; // nothing to encode.
    }

    /**
     * Encode the data associated with the sensor to the outgoing message. The
     * default method does nothing.
     *
     * @param message the BitSet representing the outgoing message.
     * @param startLocation the starting bit location in the message at which to
     * begin encoding
     * @return the ending location. That is the startLocation for the next
     * encoding.
     */
    public int encodeToValueRequest(BitSet message, int startLocation) {
        return startLocation; // nothing to encode.
    }

    /**
     * Decode the data associated with the sensor from the incoming message.
     *
     * @param message the BitSet representing the outgoing message.
     * @param startLocation the starting bit location in the message at which to
     * begin decoding
     * @return the ending location. That is the startLocation for the next
     * encoding.
     */
    public abstract int decodeValues(byte[] message, int startLocation);

    /**
     * Returns the current value.
     */
    public int getValue() {
        return value;
    }

}
