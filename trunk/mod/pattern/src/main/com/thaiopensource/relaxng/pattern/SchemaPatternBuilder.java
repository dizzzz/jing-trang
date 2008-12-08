package com.thaiopensource.relaxng.pattern;

import org.relaxng.datatype.Datatype;
import org.xml.sax.Locator;
import com.thaiopensource.xml.util.Name;

public class SchemaPatternBuilder extends PatternBuilder {
  private boolean idTypes;
  private final UnexpandedNotAllowedPattern unexpandedNotAllowed = new UnexpandedNotAllowedPattern();
  private final TextPattern text = new TextPattern();
  private final PatternInterner schemaInterner = new PatternInterner();

  public SchemaPatternBuilder() { }

  public boolean hasIdTypes() {
    return idTypes;
  }

  Pattern makeElement(NameClass nameClass, Pattern content, Locator loc) {
    Pattern p = new ElementPattern(nameClass, content, loc);
    return schemaInterner.intern(p);
  }

  Pattern makeAttribute(NameClass nameClass, Pattern value, Locator loc) {
    if (value == notAllowed)
      return value;
    Pattern p = new AttributePattern(nameClass, value, loc);
    return schemaInterner.intern(p);
  }

  Pattern makeData(Datatype dt, Name dtName) {
    noteDatatype(dt);
    Pattern p = new DataPattern(dt, dtName);
    return schemaInterner.intern(p);
  }

  Pattern makeDataExcept(Datatype dt, Name dtName, Pattern except, Locator loc) {
    noteDatatype(dt);
    Pattern p = new DataExceptPattern(dt, dtName, except, loc);
    return schemaInterner.intern(p);
  }

  Pattern makeValue(Datatype dt, Name dtName, Object value, String stringValue) {
    noteDatatype(dt);
    Pattern p = new ValuePattern(dt, dtName, value, stringValue);
    return schemaInterner.intern(p);
  }

  Pattern makeText() {
    return text;
  }

  Pattern makeOneOrMore(Pattern p) {
    if (p == text)
      return p;
    return super.makeOneOrMore(p);
  }

  Pattern makeUnexpandedNotAllowed() {
    return unexpandedNotAllowed;
  }

  Pattern makeError() {
    Pattern p = new ErrorPattern();
    return schemaInterner.intern(p);
  }

  Pattern makeChoice(Pattern p1, Pattern p2) {
    if (p1 == notAllowed || p1 == p2)
      return p2;
    if (p2 == notAllowed)
      return p1;
    return super.makeChoice(p1, p2);
  }

  Pattern makeList(Pattern p, Locator loc) {
    if (p == notAllowed)
      return p;
    Pattern p1 = new ListPattern(p, loc);
    return schemaInterner.intern(p1);
  }

  Pattern makeMixed(Pattern p) {
    return makeInterleave(text, p);
  }

  private void noteDatatype(Datatype dt) {
    if (dt.getIdType() != Datatype.ID_TYPE_NULL)
      idTypes = true;
  }
}
