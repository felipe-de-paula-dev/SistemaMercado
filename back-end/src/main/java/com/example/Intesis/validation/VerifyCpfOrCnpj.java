package com.example.Intesis.validation;

import org.springframework.stereotype.Service;

@Service
public class VerifyCpfOrCnpj {
    public boolean Validar(String documentCpfCnpj) {
        if (documentCpfCnpj.length() == 11) {
            return ValidadorCPF(documentCpfCnpj);
        } else if (documentCpfCnpj.length() == 14) {
            return ValidarCnpj(documentCpfCnpj);
        } else {
            return false;
        }
    }
    public boolean ValidadorCPF(String documento){
        if (documento.length() != 11) return false;

        //VERIFICA SE TODOS OS DIGITOS SAO DIFERENTES
        boolean todosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (documento.charAt(i) != documento.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) return false;

        //CALCULO DV1
        int dv1 = Character.getNumericValue(documento.charAt(9));
        int soma  = 0;

        for(int n = 0; n < 9; n++){
            soma += Character.getNumericValue(documento.charAt(n)) * (10 - n);
        }

        int dv1Calculo = 11 - (soma % 11);
        if (dv1Calculo >= 10) dv1Calculo = 0;

        if (dv1 != dv1Calculo) return false;

        //CALCULO DV2
        soma = 0;
        for(int n = 0; n < 10; n++){
            soma += Character.getNumericValue(documento.charAt(n)) * (11 - n);
        }

        int dv2Calculo = 11 - (soma % 11);
        if(dv2Calculo >= 10) dv2Calculo = 0;

        int dv2 = Character.getNumericValue(documento.charAt(10));
        return dv2 == dv2Calculo;
    }

    public boolean ValidarCnpj(String documento){
        if (documento.length() != 14) return false;

        //VERIFICA SE TODOS OS DIGITOS SAO DIFERENTES
        boolean todosIguais = true;
        for (int i = 1; i < 13; i++) {
            if (documento.charAt(i) != documento.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) return false;


        int dv1 = Character.getNumericValue(documento.charAt(12));
        int soma  = 0;

        int[] pesosDV1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosDV2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


        //CALCULAR DV1
        for(int n = 0; n < 12; n++){
          soma += Character.getNumericValue(documento.charAt(n)) * pesosDV1[n];
        }

        int dv1Calculo = 11 - (soma % 11);
        if (dv1Calculo >= 10) dv1Calculo = 0;
        if (dv1 != dv1Calculo) return false;

        //CALCULAR DV2
        soma = 0;
        for(int n = 0; n < 13; n++){
            soma += Character.getNumericValue(documento.charAt(n)) * pesosDV2[n];
        }

        int dv2Calculo = 11 - (soma % 11);
        if(dv2Calculo >= 10) dv2Calculo = 0;

        return Character.getNumericValue(documento.charAt(13)) == dv2Calculo;
    }



}
