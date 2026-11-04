package es.uvigo.esei.aed2.activity6.mapofmap;


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

import es.uvigo.esei.aed2.activity6.HashMap.HashMap;
import es.uvigo.esei.aed2.graph.Edge;
import es.uvigo.esei.aed2.graph.Graph;
import es.uvigo.esei.aed2.graph.Vertex;
import es.uvigo.esei.aed2.map.Map;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.xml.transform.Source;

public class MapOfMap<T, E> implements Graph<T, E> {

  private final Map<Vertex<T>, Map<Vertex<T>, E>> mapOfVertices;

  public MapOfMap() {
    this.mapOfVertices = new HashMap<>();
  }
  
  @Override
  public boolean isEmpty() {
    return mapOfVertices.size() == 0;
  }

  @Override
  public int numberOfVertices() {
    return mapOfVertices.size();
  }

  @Override
  public boolean containsVertex(Vertex<T> vertex) {
    return mapOfVertices.get(vertex) != null;
  }

  @Override
  public boolean containsEdge(Vertex<T> source, Vertex<T> target, E label) {
    if (source == null || target == null) {
      throw new NullPointerException("Source and target vertices cannot be null");
    }
    if (!containsVertex(source) || !containsVertex(target)) {
      throw new IllegalArgumentException("Source and target vertices must be in the graph");
    }
    Map<Vertex<T>, E> edgesFromSource = mapOfVertices.get(source);
    if (edgesFromSource == null) {
      return false;
    }
    E edgeLabel = edgesFromSource.get(target);
    return Objects.equals(edgeLabel, label);

    
  }

  @Override
  public Set<Vertex<T>> getVertices() {
    Set<Vertex<T>> vertices = new HashSet<>();
    mapOfVertices.forEach((vertex, edges) -> vertices.add(vertex));
    return vertices;
  }

  @Override
  public Set<Edge<T, E>> getEdges() {
    Set<Edge<T, E>> edges = new HashSet<>();
    mapOfVertices.forEach((source, edgesFromSource) -> {
      edgesFromSource.forEach((target, label) -> {
        edges.add(new Edge<>(source, target, label));
      });
    });
    return edges;
  }

  @Override
  public Set<Vertex<T>> getAdjacentsVertex(Vertex<T> vertex) throws NullPointerException {
    if (vertex == null) {
      throw new NullPointerException("Vertex cannot be null");
    }
    Map<Vertex<T>, E> edgesFromVertex = mapOfVertices.get(vertex);
    if (edgesFromVertex == null) {
      return new HashSet<>();
    }
    Set<Vertex<T>> adjacents = new HashSet<>();
    edgesFromVertex.forEach((adjacent, label) -> adjacents.add(adjacent));
    return adjacents;
  }

  @Override
  public boolean addVertex(Vertex<T> vertex) throws NullPointerException {
    if (vertex == null) {
      throw new NullPointerException("Vertex cannot be null");
    }
    if (containsVertex(vertex)) {
      return false;
    }
    mapOfVertices.add(vertex, new HashMap<>());
    return true;
  }

  @Override
  public boolean addEdge(Vertex<T> source, Vertex<T> target, E label) throws NullPointerException, IllegalArgumentException{
    if (source == null || target == null) {
      throw new NullPointerException("Source and target vertices cannot be null");
    }
    if (!containsVertex(source) || !containsVertex(target)) {
      throw new IllegalArgumentException("Source and target vertices must be in the graph");
    }
    Map<Vertex<T>, E> edgesFromSource = mapOfVertices.get(source);
    if (edgesFromSource == null) {
      edgesFromSource = new HashMap<>();
      mapOfVertices.add(source, edgesFromSource);
    }
    edgesFromSource.add(target, label);
    
    return true;
  }

  @Override
  public boolean removeVertex(Vertex<T> vertex) throws NullPointerException {
    if (vertex == null) {
      throw new NullPointerException("Vertex cannot be null");
    }
    if (!containsVertex(vertex)) {
      return false;
    }
    mapOfVertices.remove(vertex);
    mapOfVertices.forEach((source, edgesFromSource) -> edgesFromSource.remove(vertex));
    return true;
  }

  @Override
  public boolean removeEdge(Vertex<T> source, Vertex<T> target, E label) throws NullPointerException {
    if (source == null || target == null) {
      throw new NullPointerException("Source and target vertices cannot be null");
    }
    if (!containsVertex(source) || !containsVertex(target)) {
      throw new IllegalArgumentException("Source and target vertices must be in the graph");
    }
    Map<Vertex<T>, E> edgesFromSource = mapOfVertices.get(source);
    if (edgesFromSource == null) {
      return false;
    }
    E edgeLabel = edgesFromSource.get(target);
    if (!Objects.equals(edgeLabel, label)) {
      return false;
    }
    edgesFromSource.remove(target);
    return true;
  }
  
  @Override
  public void clear(){
    mapOfVertices.clear();
  }

  @Override
  public void forEachVertex(Consumer<Vertex<T>> action){
    mapOfVertices.forEach((vertex, edges) -> action.accept(vertex));
  }

  @Override
  public void forEachEdge(Consumer<Edge<T, E>> action){
    mapOfVertices.forEach((source, edgesFromSource) -> {
      edgesFromSource.forEach((target, label) -> {
        action.accept(new Edge<>(source, target, label));
      });
    });
  }

}
