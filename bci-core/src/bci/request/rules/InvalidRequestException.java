package bci.request.rules;

public class InvalidRequestException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _ruleId;

    public InvalidRequestException(int ruleId) {
        super("Erro: a requisição não respeita a regras imposta. Regra falhada: " + ruleId);
        _ruleId = ruleId;
    }

    public int getRuleId() { return _ruleId; }
    
}
