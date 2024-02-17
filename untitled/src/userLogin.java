import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class userLogin extends JFrame {
    private JLabel label, SignupUserLabel, signupPasswordLabel, signupConfirmPasswordLabel, signupEmail, userNameLabel, passwordLabel;
    private JTextField signupUserNameField, signupEmailField, userNameField;
    private JPasswordField signupPasswordField, signupConfirmField, passwordField;
    private JButton cancel, createAccount, login, signup;

    public userLogin() {
        setTitle("Westminster Shopping Login");
        setLayout(new GridBagLayout());
        initializeComponents();
        userLoginLayout();
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    // a method to initialize the labels
    private void initializeComponents() {
        label = new JLabel("WELCOME!!!");

        userNameLabel = new JLabel("Username: ");
        userNameField = new JTextField(25);
        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(10);
        login = new JButton("Login");
        signup = new JButton("Signup");

        // add an action listener to login
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());
                User user = User.getUser(username, password);
                if (user != null) {
                    User.setCurrentUser(user);
                    User presentUser = User.getCurrentUser();
                    JOptionPane.showMessageDialog(userLogin.this, "Login successful", "Done",
                            JOptionPane.INFORMATION_MESSAGE);
                    ProductGUI productGUI = new ProductGUI(presentUser);
                    productGUI.setResizable(false);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(userLogin.this, "Invalid username or password", "notDone",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // action Listener to signup
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUPMethod();
            }
        });
        //signup Details
        SignupUserLabel = new JLabel("Username: ");
        signupUserNameField = new JTextField(10);
        signupEmail = new JLabel("Email: ");
        signupEmailField = new JTextField(10);
        signupPasswordLabel = new JLabel("Password: ");
        signupPasswordField = new JPasswordField(10);
        signupConfirmPasswordLabel = new JLabel("Confirm Password: ");
        signupConfirmField = new JPasswordField(10);
        cancel = new JButton("Cancel");
        createAccount = new JButton("Create Account");
    }

    private void userLoginLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        ;

        //welcome
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(label, constraints);

        //login
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy++;
        add(userNameLabel, constraints);
        constraints.gridx = 1;
        add(userNameField, constraints);
        constraints.gridy++;
        constraints.gridx = 0;
        add(passwordLabel, constraints);
        constraints.gridx = 1;
        add(passwordField, constraints);
        constraints.gridy++;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        add(signup, constraints);
        constraints.gridx = 1;
        add(login, constraints);
    }

    private void signUPMethod() {
        JDialog signupDialog = new JDialog(this, "Signup", true);
        signupDialog.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        signupDialog.add(SignupUserLabel, constraints);
        constraints.gridx = 1;
        signupDialog.add(signupEmail, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signupDialog.add(signupUserNameField, constraints);
        constraints.gridx = 1;
        signupDialog.add(signupEmailField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signupDialog.add(signupPasswordLabel, constraints);
        constraints.gridx = 1;
        signupDialog.add(signupConfirmPasswordLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signupDialog.add(signupPasswordField, constraints);
        constraints.gridx = 1;
        signupDialog.add(signupConfirmField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signupDialog.add(cancel, constraints);
        constraints.gridx = 1;
        signupDialog.add(createAccount, constraints);

        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                try {
                    user.setUsername(signupUserNameField.getText());
                    user.setPassword(new String(signupPasswordField.getPassword()));
                    user.setEmail(signupEmailField.getText());


                    User.getUserList().add(user);
                    ArrayList<User> userList = User.getUserList();
                    System.out.println(userList);
                    JOptionPane.showMessageDialog(userLogin.this, "Account created successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // should solve how to call the save file here
                    WestminsterShoppingManager.saveFileUsers(); // not working :C , its not loadind to array

                    signupDialog.setVisible(false);
                    signupDialog.dispose();
                } catch (IllegalArgumentException error) {
                    JOptionPane.showMessageDialog(signupDialog, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupDialog.setVisible(false);
                signupDialog.dispose();
            }
        });

        signupDialog.pack();
        signupDialog.setLocationRelativeTo(this);
        signupDialog.setVisible(true);
    }
//    public static void main(String[] args) {
//        new userLogin();
//    }
}
