package org.z.entities.rules.engine;


public class RuleTrigger {
   private String name;
   public RuleTrigger(String name)
   {
      this.name=name;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
}