package es.uvigo.esei.aed2.activity3.implementation;
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


import java.util.function.Consumer;

import es.uvigo.esei.aed2.tree.binary.BinaryTree;
import es.uvigo.esei.aed2.tree.exceptions.EmptyTreeException;

import es.uvigo.esei.aed1.tads.queue.Queue;
import es.uvigo.esei.aed1.tads.queue.LinkedQueue;

public class LinkedBinaryTree<T> implements BinaryTree<T> {

  private LinkedBinaryTreeNode<T> rootNode;

  public LinkedBinaryTree() {
    rootNode = null;
  }

  public LinkedBinaryTree(T value) throws NullPointerException {
    if(value == null){
      throw new NullPointerException("Null values are not allowed");
    }
    rootNode = new LinkedBinaryTreeNode<>(value, null, null);
  }
  
  public LinkedBinaryTree(T value, BinaryTree<T> leftChild, BinaryTree<T> rightChild)
  throws NullPointerException {
    if(value == null || leftChild == null || rightChild == null){
      throw new NullPointerException("Value can not be null");
    }
    else{
      rootNode = new LinkedBinaryTreeNode<>(value, buildNodeFromTree(leftChild), buildNodeFromTree(rightChild));
    }
    
  }

  private LinkedBinaryTree(LinkedBinaryTreeNode<T> rootNode) {
    this.rootNode = rootNode;
  }

  private static <T> LinkedBinaryTreeNode<T> buildNodeFromTree(BinaryTree<T> tree){
    if (tree.isEmpty()){
      return null;
    }
    else{
      return new LinkedBinaryTreeNode<>(
        tree.getRootValue(),
        tree.hasLeftChild()?buildNodeFromTree(tree.getLeftChild()):null,
        tree.hasRightChild()?buildNodeFromTree(tree.getRightChild()):null
      );
    }
  }
  // Métodos lanzan excepción
  @Override
  public T getRootValue() throws EmptyTreeException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    return rootNode.getValue();
  }

  @Override
  public void setRootValue(T value) throws EmptyTreeException, NullPointerException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    if (value == null){
      throw new NullPointerException("Null values are not allowed");
    }
    rootNode.setValue(value);
  }

  @Override
  public boolean contains(T value) {
    return value != null && contains(rootNode, value);
  }

  private boolean contains(LinkedBinaryTreeNode<T> node, T value){
    if (node == null){
      return false;
    }
    else{
      return node.getValue().equals(value) || contains(node.getLeftNode(), value) || contains(node.getRightNode(), value);
    }
  }

  @Override
  public boolean hasLeftChild() {
    return !isEmpty() && rootNode.hasLeftNode();
  }

  @Override
  public BinaryTree<T> getLeftChild() throws EmptyTreeException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    else{
      if (hasLeftChild()){
        return new LinkedBinaryTree<>(rootNode.getLeftNode());
      }
      else{
        return new LinkedBinaryTree<>();
      }
    }
  }

  @Override
  public void setLeftChild(BinaryTree<T> leftChild) throws EmptyTreeException, NullPointerException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }

    rootNode.setLeftNode(buildNodeFromTree(leftChild));
  }

  @Override
  public void removeLeftChild() throws EmptyTreeException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    rootNode.setLeftNode(null);
  }

  @Override
  public boolean hasRightChild() {
    return !isEmpty() && rootNode.hasRightNode();
  }

  @Override
  public BinaryTree<T> getRightChild() throws EmptyTreeException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    else{
      if (hasRightChild()){
        return new LinkedBinaryTree<>(rootNode.getRightNode());
      }
      else{
        return new LinkedBinaryTree<>();
      }
    }
  }

  @Override
  public void setRightChild(BinaryTree<T> rightChild) throws EmptyTreeException, NullPointerException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }

    rootNode.setRightNode(buildNodeFromTree(rightChild));
  }

  @Override
  public void removeRightChild() throws EmptyTreeException {
    if (isEmpty()){
      throw new EmptyTreeException("The tree is empty");
    }
    rootNode.setRightNode(null);
  }
  
  @Override
  public void clear() {
    if (!isEmpty()){
      rootNode = null; 
    }
  }

  @Override
  public boolean isEmpty() {
    return rootNode == null;
  }

  private static <T> void forEachInOrder(LinkedBinaryTreeNode<T> node, Consumer<T> action){
    if (node != null){
      forEachInOrder(node.getLeftNode(), action);
      action.accept(node.getValue());
      forEachInOrder(node.getRightNode(), action);
    }
  }

  @Override
  public void forEachInOrder(Consumer<T> action) {
    if (!isEmpty()){
      forEachInOrder(rootNode, action);
    }
  }
  
  private static <T> void forEachPreOrder(LinkedBinaryTreeNode<T> node, Consumer<T> action){
    if (node != null){
      action.accept(node.getValue());
      forEachPreOrder(node.getLeftNode(), action);
      forEachPreOrder(node.getRightNode(), action);
    }
  }

  @Override
  public void forEachPreOrder(Consumer<T> action) {
    if (!isEmpty()){
      forEachPreOrder(rootNode, action);
    }
  } 

  private static <T> void forEachPostOrder(LinkedBinaryTreeNode<T> node, Consumer<T> action){
    if (node != null){
      forEachPostOrder(node.getLeftNode(), action);
      forEachPostOrder(node.getRightNode(), action);
      action.accept(node.getValue());
    }
  }

  @Override
  public void forEachPostOrder(Consumer<T> action) {
    if (!isEmpty()){
      forEachPostOrder(rootNode, action);
    }
  } 

  @Override
  public void forEachLevelOrder(Consumer<T> action) {
    Queue<LinkedBinaryTreeNode<T>> queue = new LinkedQueue<>();
    queue.add(rootNode);

    do {
      LinkedBinaryTreeNode<T> node = queue.remove();
      if (node != null) {
        action.accept(node.getValue());
        if (node.hasLeftNode()) {
          queue.add(node.getLeftNode());
        }
        if (node.hasRightNode()) {
          queue.add(node.getRightNode());
        }
      }
    }while (!queue.isEmpty());
  }

}