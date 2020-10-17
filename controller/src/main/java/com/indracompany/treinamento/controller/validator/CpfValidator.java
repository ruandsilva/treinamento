package com.indracompany.treinamento.controller.validator;

import java.util.InputMismatchException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.stereotype.Component;

@Component(value = "cpfValidator")
@FacesValidator
public class CpfValidator implements Validator {

  public static final String CPF_INVALIDO = "CPF é inválido.";

  public static boolean isValid(String cpf) {
    cpf = remove(String.valueOf(cpf));
    return validaCPF(cpf);
  }

  private static String remove(String cpf) {
    cpf = cpf.replace(".", "");
    cpf = cpf.replace("-", "");
    return cpf;
  }

  private static boolean validaCPF(final String cpf) {
    if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444")
        || cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")
        || cpf.length() != 11 || cpf.length() == 14) {
      return false;
    }
    char dig10;
    char dig11;
    int sm = 0;
    int i = 0;
    int r;
    int num;
    int peso = 10;
    try {
      for (; i < 9; i++) {
        num = cpf.charAt(i) - 48;
        sm = sm + num * peso;
        peso = peso - 1;
      }
      r = 11 - sm % 11;
      if (r == 10 || r == 11) {
        dig10 = '0';
      } else {
        dig10 = (char) (r + 48);
      }
      sm = 0;
      peso = 11;
      for (i = 0; i < 10; i++) {
        num = cpf.charAt(i) - 48;
        sm = sm + num * peso;
        peso = peso - 1;
      }
      r = 11 - sm % 11;
      if (r == 10 || r == 11) {
        dig11 = '0';
      } else {
        dig11 = (char) (r + 48);
      }

      return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);
    } catch (final InputMismatchException erro) {
      return false;
    }
  }

  @Override
  public void validate(final FacesContext arg0, final UIComponent arg1, Object valorTela) {
    valorTela = remove(String.valueOf(valorTela));
    if (!validaCPF(String.valueOf(valorTela))) {
      final FacesMessage message = new FacesMessage();
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      message.setSummary(CpfValidator.CPF_INVALIDO);
      throw new ValidatorException(message);
    }
  }

}
