import dao.DAO_user;
import dao.User;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    List<User> userList = new ArrayList<>();
    String textRecording;
    DAO_user dao_user=new DAO_user();

    Bot() {
        //textRecording=connectToDatabase();
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    public void sendingMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
      getLocalPhoto(update);
        Message message = update.getMessage();

        String[] options = message.getText().split("\\s+");
        //String[] options={"help"};
        if (message != null && message.hasText()) {
            switch (options[0]) {
                case "/help":
                    sendingMessage(message, "Здравствуйте,чем я могу вам помочь?");
                    // userList.add(new dao.User("Ivan",27));
                    break;
                case "/findTrainer":
                    String result = findTrainer(options[1]);
                    sendingMessage(message, result);
                    break;
                case "/settings":
                    sendingMessage(message, "dvkmvd");
                case "/picture":
                    break;
                case "/registration":
                    StringBuffer sb=new StringBuffer();
                 for(String temp:options)   {
                     sb.append(temp);
                 }
                 String par2=sb.toString();
                 String[] regParam=par2.split(":");
                 String name = regParam[0];
                    String surname = regParam[1];
                    String email = regParam[2];
                    String numberOfPhone = regParam[3];
                    String login = regParam[4];
                    String password = regParam[5];
                    String typeOfUser = regParam[6];
                    User user=new User("Andrei","Andreev","ddd@.net","380662339122","user","user1","client");
                    dao_user.create(user);
                    System.out.println("Congrat,your have been logined");
                default:

            }
        }

    }

    public void getLocalPhoto(Update getUpdate) {
        if (getUpdate.hasMessage() && getUpdate.getMessage().hasPhoto()) {

            long chatId = getUpdate.getMessage().getChatId();
            List<PhotoSize> photos = getUpdate.getMessage().getPhoto();

            String fileId = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();

            int fileWidth = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();

            int fileHeight = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();
            String title = "file_id: " + fileId + "\nwidth: " + Integer.toString(fileWidth) + "\nheight: " + Integer.toString(fileHeight);
            SendPhoto message = new SendPhoto()
                    .setChatId(chatId)
                    .setPhoto(fileId)
                    .setCaption(title);

          }
}
    private String findTrainer(String argument) {
        final String DATA_DRIVER = "org.sqlite.JDBC";
        final String DATA_URL = "jdbc:sqlite:D:/123/Fitness_DB.db";
        String result = "";
        try {
            Class.forName(DATA_DRIVER);

            Connection connection = null;

            connection = DriverManager.getConnection(DATA_URL);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Users where name like '%" + argument.trim() + "%'");
            while (resultSet.next() == true) {
                result += resultSet.getString(2);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.isEmpty() ? "К сожалению,такого тренера не сущевствует" : "Ваш тренер найден";
    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow keyboardFirst = new KeyboardRow();
        keyboardFirst.add(new KeyboardButton("/help"));
        keyboardFirst.add(new KeyboardButton("/settings"));
        keyboardFirst.add(new KeyboardButton("/findTrainer"));
        keyboardRow.add(keyboardFirst);
        replyKeyboardMarkup.setKeyboard(keyboardRow);

    }


    @Override
    public String getBotUsername() {
        return "YourFitnessHelper_bot";
    }

    @Override
    public String getBotToken() {
        return "938877110:AAFHC61DdkgRYLjKDZw1HPWWXzp87nfHF9A";
    }
}
