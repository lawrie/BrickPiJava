/*
 *  Copyright ErgoTech Systems, Inc 2014
 *
 * This file is made available online through a Creative Commons Attribution-ShareAlike 3.0  license.
 * (http://creativecommons.org/licenses/by-sa/3.0/)
 *
 *  This is a library of functions for the RPi to communicate with the BrickPi.
 */
package com.ergotech.brickpi.sensors;

import com.ergotech.brickpi.BrickPi;

/**
 * Representation of a Touch Sensor.
 */
public class ColorSensorFull extends Sensor {
	int[] array = new int[4];
    /**
     * Returns an instance of this sensor.
     */
    public ColorSensorFull() {

    }

    @Override
    public int decodeValues(byte[] message, int startLocation) {
        value = BrickPi.decodeInt(3, message, startLocation);
        array[3] = BrickPi.decodeInt(10, message, startLocation+3);
        array[0] = BrickPi.decodeInt(10, message, startLocation+13);
        array[1] = BrickPi.decodeInt(10, message, startLocation+23);
        array[2] = BrickPi.decodeInt(10, message, startLocation+33);
        return startLocation + 43;
    }

    @Override
    public byte getSensorType() {
        return TYPE_SENSOR_COLOR_FULL;
    }

}
