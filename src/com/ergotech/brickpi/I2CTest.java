package com.ergotech.brickpi;

import com.ergotech.brickpi.sensors.I2CSensor;

public class I2CTest {
    public static void main(String[] args) throws Exception {
        BrickPi brickPi = BrickPi.getBrickPi();
        // Add a Mindsensors acceleration sensor in tilt mode
        brickPi.setSensor(new I2CSensor(1, 10, true, false, new byte[] {0x42}, 3), 0);
        brickPi.setupSensors();

        for (;;) {
            Thread.sleep(200); 
            I2CSensor s  = ((I2CSensor) brickPi.getSensor(0));
            System.out.println("Tilt: X:" + s.getValue(0,0) + " Y:" + s.getValue(0,1) + " Z:" + s.getValue(0,2));
        }
    }
}