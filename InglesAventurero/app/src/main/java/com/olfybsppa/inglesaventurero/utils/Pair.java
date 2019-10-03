package com.olfybsppa.inglesaventurero.utils;

public class Pair<K,L> {
  public K first;
  public L second;

  public Pair (K first, L second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean equals (Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;

    Pair<K,L> other = (Pair<K,L>)obj;

    boolean returnBoolean =
      (first == other.first || first != null && first.equals(other.first)) &&
        (second == other.second) || second != null && second.equals(other.second);

    return returnBoolean;

  }

}
