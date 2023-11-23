package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandingProximityIterator implements Iterator<Driver> {
  private boolean check;
  private int value;
  private final Iterable<Driver> driverPool;
  private final Position clientPosition;
  private final int expansionStep;
  private Driver nextDriver;
  private Iterator<Driver> driverIterator;

  public ExpandingProximityIterator(
      Iterable<Driver> driverPool, Position clientPosition, int expansionStep) {

    if (driverPool == null) {
      throw new IllegalArgumentException("Error Occurred.");
    }
    if (clientPosition == null) {
      throw new IllegalArgumentException("Error Occurred.");
    }
    this.check = false;
    this.value = 0;
    this.driverPool = driverPool;
    this.clientPosition = clientPosition;
    this.expansionStep = expansionStep;
    this.nextDriver = null;
    this.driverIterator = driverPool.iterator();
  }

  public boolean hasNext() {
    DriverMethod();
    return this.nextDriver != null;
  }

  public Driver next() {
    if (this.hasNext()) {
      Driver temp = nextDriver;
      nextDriver = null;
      return temp;
    } else {
      throw new NoSuchElementException("Error occurred.");
    }
  }

  private void DriverMethod() {
    while (this.nextDriver == null) {
      if (!driverIterator.hasNext()) {
        if (check) {
          value++;
          check = false;
          driverIterator = driverPool.iterator();
        } else {
          break;
        }
      } else {
        Driver potential = driverIterator.next();
        int driverDistance =
            clientPosition.getManhattanDistanceTo(potential.getVehicle().getPosition());
        if (driverDistance > 1 + ((value - 1) * expansionStep)) {
          if (driverDistance > 1 + (value * expansionStep)) {
            check = true;
          } else {
            nextDriver = potential;
          }
        }
      }
    }
  }
}
