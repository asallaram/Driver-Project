package com.comp301.a05driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SnakeOrderAcrossPoolsIterator implements Iterator<Driver> {
  private final List<Iterator<Driver>> driverIteratorList;
  private int index;
  private Driver nextDriver;
  private int finished;
  private int location;

  public SnakeOrderAcrossPoolsIterator(List<Iterable<Driver>> driverPools) {
    this.nextDriver = null;
    this.location = 1;
    this.finished = 0;
    driverIteratorList = new ArrayList<>();
    for (int i = 0; i < driverPools.size(); i++) {
      driverIteratorList.add(driverPools.get(i).iterator());
      if (!driverIteratorList.get(i).hasNext()) {
        finished++;
      }
    }
    index = 0;
  }

  public boolean hasNext() {
    snakeOrderHelper();
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

  private void snakeOrderHelper() {
    if (nextDriver != null) {
      return;
    }

    while (finished < driverIteratorList.size()) {
      while (!driverIteratorList.get(index).hasNext()) {
        index += location;
        if (index == driverIteratorList.size()) {
          location = -1;
          index = driverIteratorList.size() - 1;
        }
        if (index == -1) {
          location = 1;
          index = 0;
        }
      }
      nextDriver = driverIteratorList.get(index).next();
      if (!driverIteratorList.get(index).hasNext()) {
        finished++;
      }
      index += location;
      if (index == driverIteratorList.size()) {
        location = -1;
        index = driverIteratorList.size() - 1;
      }
      if (index == -1) {
        location = 1;
        index = 0;
      }
      break;
    }
  }
}
