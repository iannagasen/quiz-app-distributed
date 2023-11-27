package dev.agasen.microsrv.api.core.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
  private Long id;
  private String value;
  private String explanation;
  private boolean correct;
}
