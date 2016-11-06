
package full_stack;

import javax.swing.*;
import java.awt.*;

public class TaxReturn extends JFrame {
    
    public TaxReturn(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DefaultListModel model=new DefaultListModel();
        
        JList list=new JList(model);
       // list.setBackground(Color.GRAY);
        list.setBorder(BorderFactory.
                createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), 
                        BorderFactory.createLoweredBevelBorder()));
        model.addElement("Individual tax return");
        model.addElement("Partnership report");
        model.addElement("Corporate report");
        
        JPanel panel=new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300,200));
        panel.add(list, BorderLayout.CENTER);
        
        JPanel panel_1=new JPanel(new FlowLayout());
        panel_1.setBackground(Color.lightGray);
        JButton button1=new JButton("OK");
        JButton button2=new JButton("Cancel");
        panel_1.add(button1);
        panel_1.add(button2);
        panel.add(panel_1, BorderLayout.SOUTH);
        add(panel);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args){
        new TaxReturn();
        
        
        
    }
    
}
