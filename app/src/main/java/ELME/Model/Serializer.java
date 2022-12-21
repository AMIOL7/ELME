/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ELME.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author andru
 */
public class Serializer {
    
    /**
     * Convert a number given in decimal for to binary form
     * @param num number in decimal for to convert
     * @return number in binary form
     */
    private int decimalToBinary(int num) {
        int result = 0;
        int cnt = 0;
        while (num != 0) {
            int rem = num % 2;
            double c = Math.pow(10, cnt);
            result += rem * c;
            num /= 2;
            cnt++;
        }
        return result;
    }
    /**
     * Creates a truth table of a graph
     * @param g graph for which the truth table is created
     * @return Map of input - output sequence that make the truth table.
     * Each sequence is in binary form
     */
    public Map<Integer,Integer> generateTruthTable(Graph g) {
        Map<Integer,Integer> truth_table = new HashMap<>();
        ArrayList<InputPort> freeInputPorts = g.getFreeInputPorts();
        ArrayList<OutputPort> freeOutputPorts = g.getFreeOutputPorts();
        ArrayList<OutputPort> tempOutputs = new ArrayList<>();
        
        int numOfFreeInputs = freeInputPorts.size();
        for(int i=0;i<numOfFreeInputs;i++) {
            OutputPort o = new OutputPort(Integer.toString(i),null);
            freeInputPorts.get(i).connect(o);
            tempOutputs.add(o);
        }
        
        
        for(int i=0;i<Math.pow(2, numOfFreeInputs);i++) {
            int b_num = decimalToBinary(i);
            for(int j=numOfFreeInputs-1;i>=0;i--) {
                tempOutputs.get(j).setValue(Optional.ofNullable((b_num % 10) == 1));
                b_num /= 10;
            }
            g.evaluateImpl();
            int out = 0;
            int cnt = freeOutputPorts.size()-1;
            for(int j=0;j<freeOutputPorts.size();j++) {
                int val = (freeOutputPorts.get(j).getValue().get()) ? 1 : 0;
                out += Math.pow(10, cnt)*val;
            }
            truth_table.put(decimalToBinary(i), out);
        }
        return truth_table;
    }
}
