package com.thaiopensource.relaxng.impl;

import java.util.Enumeration;
import java.util.Hashtable;

class Grammar {
  private Hashtable patterns = new Hashtable();

  private RefPattern start = new RefPattern(null);

  private Grammar parent;

  Grammar(Grammar parent) {
    this.parent = parent;
  }

  RefPattern startPatternRef() {
    return start;
  }

  Grammar getParent() {
    return parent;
  }

  Enumeration patternNames() {
    return patterns.keys();
  }

  RefPattern makePatternRef(String name) {
    RefPattern p = (RefPattern)patterns.get(name);
    if (p == null) {
      p = new RefPattern(name);
      patterns.put(name, p);
    }
    return p;
  }

}
