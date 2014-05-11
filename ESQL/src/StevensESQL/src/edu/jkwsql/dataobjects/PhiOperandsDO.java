package edu.jkwsql.dataobjects;

import java.util.List;

/**
 * Created by Dimitri on 4/18/14.
 * Data Object for storing Phi operands
 */
public class PhiOperandsDO {
    // List of edu.jkwsql.dataobjects.PhiOperandsDO operands
    private List<String> S;
    private int n = 0;
    private List<String> V;
    private List<String> F;
    private List<String> sigma;
    private boolean isExecuteQuery;
    private boolean isGenerateOutputFile;

    public PhiOperandsDO() {
    }

    // Simple constructor
    public PhiOperandsDO(List S, int n, List V, List F, List sigma, boolean isExecuteQuery, boolean isGenerateOutputFile) {
        this.S = S;
        this.n = n;
        this.V = V;
        this.F = F;
        this.sigma = sigma;
        this.isExecuteQuery = isExecuteQuery;
        this.isGenerateOutputFile = isGenerateOutputFile;
    }

    @Override
    public String toString() {
        return "PhiOperands [" +
                "S=" + S + ", " +
                "n=" + n + ", " +
                "V=" + V + ", " +
                "F=" + F + ", " +
                "sigma=" + sigma + ", " +
                "]";
    }

    public List getS() {
        return S;
    }

    public int getN() {
        return n;
    }

    public List getV() {
        return V;
    }

    public List getF() {
        return F;
    }

    public List getSigma() {
        return sigma;
    }

    public boolean isExecuteQuery() {
        return isExecuteQuery;
    }

    public boolean isGenerateOutputFile() {
        return isGenerateOutputFile;
    }
}
