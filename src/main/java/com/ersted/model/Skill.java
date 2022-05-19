package com.ersted.model;

import java.util.Objects;

public class Skill extends BaseEntity {
    protected String skill;

    public Skill(String skill) {
        this.skill = skill;
    }

    public Skill(Long id,String skill) {
        this.id = id;
        this.skill = skill;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill1 = (Skill) o;
        return this.skill.equals(skill1.getSkill()) && this.id.equals(skill1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill);
    }
}
