package es.uvigo.esei.aed2.activity4;

/*-
 * #%L
 * AEDII - Activities
 * %%
 * Copyright (C) 2025 Rosalía Laza Fidalgo, María Reyes Pavón Rial,
 * Florentino Fernández Riverola, María Novo Lourés, and Miguel Reboiro Jato
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.util.ArrayList;

public class BinaryMaxHeap<T extends Comparable<T>> implements MaxHeap<T> {

  // Exercise 1
  private final ArrayList<T> arrayHeap;

  public BinaryMaxHeap() {
    this.arrayHeap = new ArrayList<>();
    this.arrayHeap.add(0, null);
  }

  /**
   * Comprueba si el heap está vacío.
   *
   * @return <code>true</code> cuando el heap está vacío y <code>false</code> en
   * caso contrario.
   */
  @Override
  public boolean isEmpty() {
    return this.arrayHeap.size() == 1;
  }

  /**
   * Recupera el mayor elemento del heap.
   *
   * @return el elemento mayor del heap.
   * @throws HeapEmptyException cuando el heap está vacío.
   */
  @Override
  public T getMaxValue() throws HeapEmptyException {
    if (this.isEmpty()) {
      throw new HeapEmptyException("Heap is empty");
    }
    return this.arrayHeap.get(1);
  }

  /**
   * Elimina y devuelve el elemento mayor del heap.
   *
   * @return el elemento mayor del heap.
   * @throws HeapEmptyException cuando el heap está vacío.
   */
  @Override
  public T removeMaxValue() throws HeapEmptyException {
    if (this.isEmpty()) {
      throw new HeapEmptyException("Heap is empty");
    }

    T maxValue = this.arrayHeap.get(1);
    T lastValue = this.arrayHeap.remove(this.arrayHeap.size() - 1);

    if (!this.isEmpty()) {
      this.arrayHeap.set(1, lastValue);
      this.sink(1);
    }

    return maxValue;
  }

  /**
   * Mueve el elemento en la posición {@code hollow} hacia arriba en el heap
   * hasta que se cumpla la propiedad de ordenación.
   *
   * @param hollow la posición del elemento a mover.
   */
  private void sink(int hollow) {
    while (2 * hollow < this.arrayHeap.size()) {
      int child = 2 * hollow;

      if (child + 1 < this.arrayHeap.size()
          && this.arrayHeap.get(child + 1).compareTo(this.arrayHeap.get(child)) > 0) {
        child++;
      }

      if (this.arrayHeap.get(hollow).compareTo(this.arrayHeap.get(child)) >= 0) {
        break;
      }

      T temporary = this.arrayHeap.get(hollow);
      this.arrayHeap.set(hollow, this.arrayHeap.get(child));
      this.arrayHeap.set(child, temporary);
      hollow = child;
    }
  }

  /**
   * Añade un valor al heap.
   *
   * @param value el elemento a añadir.
   * @throws NullPointerException si el valor es <code>null</code>.
   */
  @Override
  public void add(T value) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException("The value cant be null");
    }

    this.arrayHeap.add(value);

    int child = this.arrayHeap.size() - 1;
    while (child > 1) {
      int parent = child / 2;
      if (this.arrayHeap.get(child).compareTo(this.arrayHeap.get(parent)) <= 0) {
        break;
      }

      T temp= this.arrayHeap.get(parent);
      this.arrayHeap.set(parent, this.arrayHeap.get(child));
      this.arrayHeap.set(child, temp);
      child = parent;
    }
  }

  /**
   * Elimina los valores del heap, convirtiéndolo en un heap vacío.
   */
  @Override
  public void clear() {
    this.arrayHeap.clear();
    this.arrayHeap.add(0, null);
  }

  /**
   * Añade un valor al heap sin mantener la propiedad de ordenacion
   *
   * @param value el elemento a añadir
   * @throws NullPointerException si el valor es <code>null</code>
   */
  @Override
  public void insert(T value) throws NullPointerException {
    if (value == null) {
      throw new NullPointerException("The value cant be null");
    }

    this.arrayHeap.add(value);
  }

  /**
   * Restablece el orden en el montículo binario
   */
  @Override
  public void orderHeap() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}