/*
 *  Copyright ErgoTech Systems, Inc 2014
 *
 * This file is made available online through a Creative Commons Attribution-ShareAlike 3.0  license.
 * (http://creativecommons.org/licenses/by-sa/3.0/)
 *
 *  This is a library of functions for the RPi to communicate with the BrickPi.
 */
package com.ergotech.brickpi.sensors;

import java.util.ArrayList;
import java.util.BitSet;

import com.ergotech.brickpi.BrickPi;

/**
 *
 * @author jim
 */
public class I2CSensor extends Sensor {
	private int numDevices = 0;
	private int speed = 10;
	ArrayList<I2CDevice> devices = new ArrayList<I2CDevice>();
	
	public I2CSensor(int address, int speed, boolean same, boolean legoMode, byte[] data, int readLen) {
		this.speed = speed;
		addDevice(address,same, legoMode,data,readLen);
	}
	
	public void addDevice(int address, boolean same, boolean legoMode, byte[] data, int readLen) {
		I2CDevice d = new I2CDevice();
		d.address = address;
		d.data = data;
		d.readLen = readLen;
		d.writeLen = (data == null ? 0 :data.length);
		d.buffer = new byte[readLen];
		d.settings = (same ? 2 : 0) | (legoMode ? 1 : 0);
		d.same = same;
		devices.add(d);
		numDevices++;
	}
	
	public void setData(int index, byte[] data, int readLen) {
		I2CDevice d = devices.get(index);
		d.data = data;
		d.writeLen = data.length;
		d.readLen = readLen;
	}

    @Override
    public byte getSensorType() {
        return TYPE_SENSOR_I2C;
    }
    
    @Override
    public int encodeToSetup(BitSet message, int startLocation) {
    	// Send speed
        int tmp = speed;;
        for (int counter = 0; counter < 8; counter++) { // 8 bits
            message.set(startLocation++, (tmp & 0x1) == 1);
            tmp >>= 1;
        }
        // Send number of devices
        tmp = numDevices - 1;
        for (int counter = 0; counter < 3; counter++) { // 3 bits
            message.set(startLocation++, (tmp & 0x1) == 1);
            tmp >>= 1;
        }
        // Send i2c data
        for(int i=0;i<numDevices;i++) {
        	I2CDevice d = devices.get(i);
        	tmp = d.address;
            for (int counter = 0; counter < 7; counter++) { // 7 bits
                message.set(startLocation++, (tmp & 0x1) == 1);
                tmp >>= 1;
            }
        	tmp = d.settings;
            for (int counter = 0; counter < 2; counter++) { // 2 bits
                message.set(startLocation++, (tmp & 0x1) == 1);
                tmp >>= 1;
            }
            if (d.same) { // i2c command will not change
            	// send the write length 
            	tmp = d.writeLen;
                for (int counter = 0; counter < 4; counter++) { // 4 bits
                    message.set(startLocation++, (tmp & 0x1) == 1);
                    tmp >>= 1;
                }
                // send the read length
                tmp = d.readLen;
                for (int counter = 0; counter < 4; counter++) { //  bits
                    message.set(startLocation++, (tmp & 0x1) == 1);
                    tmp >>= 1;
                }
                // Send the i2c write bytes
                for(int j=0;j<d.writeLen;j++) {
                	tmp = d.data[j];
	                for (int counter = 0; counter < 8; counter++) { // 8-bits
	                    message.set(startLocation++, (tmp & 0x1) == 1);
	                    tmp >>= 1;
	                }
                }
            }
        }
        return startLocation; 
    }
    
    @Override
    public int encodeToValueRequest(BitSet message, int startLocation) {
    	for(int i=0;i<numDevices;i++) {
    		I2CDevice d = devices.get(i);
    		if ((!d.same)) {
            	// send the write length 
            	int tmp = d.writeLen;
                for (int counter = 0; counter < 4; counter++) { // 4 bits
                    message.set(startLocation++, (tmp & 0x1) == 1);
                    tmp >>= 1;
                }
                // send the read length
                tmp = d.readLen;
                for (int counter = 0; counter < 4; counter++) { //  bits
                    message.set(startLocation++, (tmp & 0x1) == 1);
                    tmp >>= 1;
                }
                // Send the i2c write bytes
                for(int j=0;j<d.writeLen;j++) {
                	tmp = d.data[j];
	                for (int counter = 0; counter < 8; counter++) { // 8-bits
	                    message.set(startLocation++, (tmp & 0x1) == 1);
	                    tmp >>= 1;
	                }
                }
    		}
    	}
        return startLocation; 
    }

    @Override
    public int decodeValues(byte[] message, int startLocation) {
    	int deviceBits = BrickPi.decodeInt(numDevices, message, startLocation);
    	startLocation += numDevices;
    	
    	for(int i=0;i<numDevices;i++) {
    		I2CDevice d = devices.get(i);
    		if ((deviceBits & (1 << i)) == 1) {
    			for(int j=0;j<d.readLen;j++) {
    				d.buffer[j] = (byte) BrickPi.decodeInt(8, message, startLocation);
    				startLocation += 8;
    			}
    		}
    	}	
        return startLocation;
    } 
    
    public int getValue(int i, int j) {
    	return devices.get(i).buffer[j];
    }
    
    class I2CDevice {
    	int address;
    	int settings;
    	boolean same;
    	int readLen, writeLen;
    	byte[] buffer;
    	byte[] data;
    }
}
