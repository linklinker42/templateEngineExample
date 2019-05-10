package com.mycompany.templateengine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
//import org.thymeleaf.templateresolver.

/**
 *
 * @author Christopher J. Weeks
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    runTemplate();
  }

  public static class CustomObject {

    private final String text = "Hello World!";

    @Override
    public String toString() {
      return "CustomObject{" + "text=" + text + '}';
    }

  }

  public static class TestItem {

    private final String string;
    public final int integer;
    public final float floatValue;
    public final Date date;
    public final CustomObject customObject = new CustomObject();

    public TestItem(final String string, final int integer, final float floatValue, final Date date) {
      this.string = string;
      this.integer = integer;
      this.floatValue = floatValue;
      this.date = date;
    }

    public String getString() {
      return string;
    }

  }

  private static void runTemplate() {
    final TemplateEngine templateEngine = new TemplateEngine();
    final StringTemplateResolver resolver = new StringTemplateResolver();
    resolver.setTemplateMode(TemplateMode.TEXT);
    templateEngine.setTemplateResolver(resolver);

    final Context context = new Context();
    final List<TestItem> list = new ArrayList<>();
    list.add(new TestItem("one", 1, 1.2f, new Date(203, 1, 5)));
    list.add(new TestItem("two", 2, 2.2f, new Date(202, 3, 1)));
    list.add(new TestItem("three", 3, 3.2f, new Date(201, 4, 3)));
    context.setVariable("items", list);
    final String template = "[#th:block th:each=\"item : ${items}\"]\n"
      + "  - [#th:block th:utext=\"${item.string}\" /]\n"
      + "  - [#th:block th:utext=\"${item.integer}\" /]\n"
      + "  - [#th:block th:utext=\"${item.floatValue}\" /]\n"
      + "  - [#th:block th:utext=\"${item.date}\" /]\n"
      + "  - [#th:block th:utext=\"${item.customObject}\" /]\n"
      + "[/th:block]";

    final String result = templateEngine.process(template, context);
    System.out.println(result);
  }
}
