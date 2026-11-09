package edu.illinois.cs.cs124.ay2023.mp.models;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Model holding the course summary information shown in the summary list.
 *
 * @noinspection unused
 */
public class Summary implements Comparable<Summary> {
  private String subject;

  public static List<Summary> filter(List<Summary> list, String filter) {
    String f = filter.trim().toLowerCase();
    List<Summary> l = new ArrayList<>();
    for (Summary a : list) {
      if (a.toString().toLowerCase().contains(f)) {
        l.add(a);
      }
    }
    Collections.sort(l);
    l.sort((first, second) -> {
      return first.toString().toLowerCase().indexOf(f) - second.toString().toLowerCase().indexOf(f);
    });
    return l;
  }

  /**
   * Get the subject for this Summary.
   *
   * @return the subject for this Summary
   */
  @NotNull
  public final String getSubject() {
    return subject;
  }

  private String number;

  /**
   * Get the number for this Summary.
   *
   * @return the number for this Summary
   */
  @NotNull
  public final String getNumber() {
    return number;
  }

  private String label;

  /**
   * Get the label for this Summary.
   *
   * @return the label for this Summary
   */
  @NotNull
  public final String getLabel() {
    return label;
  }

  /** Create an empty Summary. */
  public Summary() {}

  /**
   * Create a Summary with the provided fields.
   *
   * @param setSubject the department for this Summary
   * @param setNumber the number for this Summary
   * @param setLabel the label for this Summary
   */
  public Summary(@NonNull String setSubject, @NonNull String setNumber, @NotNull String setLabel) {
    subject = setSubject;
    number = setNumber;
    label = setLabel;
  }

  /** {@inheritDoc} */
  @NonNull
  @Override
  public String toString() {
    return subject + " " + number + ": " + label;
  }

  public int compareTo(Summary o) {
    if (number.compareTo(o.number) != 0) {
      return number.compareTo(o.number);
    }
    return subject.compareTo(o.subject);
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Summary summary = (Summary) o;
    return subject.equals(summary.subject) && number.equals(summary.number);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, number);
  }
}


