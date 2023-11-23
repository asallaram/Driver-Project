package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ProximityIterator implements Iterator<Driver> {
  private final Iterator<Driver> driverPoolIterator;
  private final Position clientPosition;
  private final int proximityRange;
  private Driver nextDriver;

  public ProximityIterator(
      Iterable<Driver> driverPool, Position clientPosition, int proximityRange) {
    if (driverPool == null) {
      throw new IllegalArgumentException("driverPool can't be null");
    }
    if (clientPosition == null) {
      throw new IllegalArgumentException("clientPosition can't be null");
    }
    this.driverPoolIterator = driverPool.iterator();
    this.clientPosition = clientPosition;
    this.proximityRange = proximityRange;
    this.nextDriver = null;
  }

  private void loadNextDriver() {
    while (driverPoolIterator.hasNext()) {
      Driver driver = driverPoolIterator.next();
      if (driver.getVehicle().getPosition().getManhattanDistanceTo(clientPosition)
          <= proximityRange) {
        nextDriver = driver;
        return;
      }
    }
    nextDriver = null;
  }

  @Override
  public boolean hasNext() {
    if (nextDriver == null) {
      loadNextDriver();
    }
    return nextDriver != null;
  }

  @Override
  public Driver next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    Driver result = nextDriver;
    nextDriver = null;
    return result;
  }
}
