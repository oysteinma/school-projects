package it1901.groups2021.gr2141.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
/**
 * A class that lets its subclasses execute a set of subscriber functions every.
 * time something changes.
 */

public abstract class Observable<T> {

  private Collection<Consumer<T>> subscribers = new ArrayList<>();

  /**
   * Add a function to execute whenever {@link #updateSubscribers
   * updateSubscribers} is run.
   *
   * @param function An executable
   */
  public void subscribe(Consumer<T> function) {
    this.subscribers.add(function);
  }

  /**
   * Execute all subscriber functions.
   *
   * @param newValue the new state of the object.
   */
  public void updateSubscribers(T newValue) {
    this.subscribers.forEach((s) -> s.accept(newValue));
  }

  /**
   * Remove all subscriber functions.
   */
  public void clearSubscribers() {
    this.subscribers.clear();
  }
}