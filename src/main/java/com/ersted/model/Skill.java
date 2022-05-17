package com.ersted.model;

public class Skill extends BaseEntity {
    protected String skill;

    public Skill(String skill) {
        this.skill = skill;
    }

    public Skill() {
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skill='" + skill + '\'' +
                '}';
    }
}
