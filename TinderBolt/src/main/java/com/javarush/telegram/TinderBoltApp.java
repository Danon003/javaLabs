package com.javarush.telegram;

import com.javarush.telegram.ChatGPTService;
import com.javarush.telegram.DialogMode;
import com.javarush.telegram.MultiSessionTelegramBot;
import com.javarush.telegram.UserInfo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;

public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "******************";
    public static final String TELEGRAM_BOT_TOKEN = "********************";
    public static final String OPEN_AI_TOKEN = "**************************";
    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }
    private ChatGPTService chatgpt = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode currentMode = null;
    private ArrayList<String> list = new ArrayList<>();
    private UserInfo me;
    private UserInfo she;
    private int questionCount;

    @Override
    public void onUpdateEventReceived(Update update) {
        String message = getMessageText();

        if(message.equals("/start")) {
            currentMode = DialogMode.MAIN;
            sendPhotoMessage("main");
            String text = loadMessage("main");
            sendTextMessage(text);

            showMainMenu("Начало", "/start",
                    "Сгенерировать Tinder-профиль\uD83D\uDE0E", "/profile",
                    "Сгененрировать сообщение для знакомства\uD83E\uDD70", "/opener ",
                    "Начать переписку от моего имени\uD83D\uDE08", "/message",
                    "Начать переписку со звездами\uD83D\uDD25", "/date",
                    "Начать общение с ChatGPT\uD83E\uDDE0", "/gpt");
            return;
        }

        if(message.equals("/gpt")){
            currentMode = DialogMode.GPT;
            sendPhotoMessage("gpt");
            String text = loadMessage("gpt");
            sendTextMessage(text);
            return;
        }
        if(currentMode == DialogMode.GPT && !isMessageCommand()){
            String prompt = loadPrompt("gpt");
            Message msg = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
            String answer = chatgpt.sendMessage(prompt, message);
            updateTextMessage(msg, answer);
            return;
        }

        //command DATE
        if(message.equals("/date")){
            currentMode = DialogMode.DATE;
            sendPhotoMessage("date");
            String text = loadMessage("date");

            sendTextButtonsMessage(text,
                    "Ариана Гранде", "date_grande",
                    "Марго Робби", "date_robbie",
                    "Зендея", "date_zendaya",
                    "Райан Гослинг", "date_gosling",
                    "Том Харди", "date_hardy");
            return;
        }

        if(currentMode == DialogMode.DATE && !message.equals("/message")&& !isMessageCommand()){
            String query = getCallbackQueryButtonKey();
            if(query.startsWith("date_")){
                sendPhotoMessage(query);
                sendTextMessage(" Отличный выбор!\nТвоя задача пригласить девушку/парня на свидание за 5 сообщений.");

                String prompt = loadPrompt(query);
                chatgpt.setPrompt(prompt);
                return;
            }
            Message msg = sendTextMessage("Подождите, девушка набирает текст...");
            String answer = chatgpt.addMessage( message);
            updateTextMessage(msg, answer);
            return;
        }

        //command Message
        if(message.equals("/message")){
            currentMode = DialogMode.MESSAGE;
            sendPhotoMessage("message");
            String text = loadMessage("message");
            sendTextButtonsMessage(text,
            "Следующее сообщение" , "message_next",
            "Пригласить на свидание", "message_date");

            return;
        }

        if(currentMode == DialogMode.MESSAGE && !isMessageCommand()){
            String query = getCallbackQueryButtonKey();
            if (query.startsWith("message_")) {
                String prompt = loadPrompt(query );
                String userChatHistory = String.join("\n\n", list);

                Message msg = sendTextMessage("Подождите пару секунд - ChatGPT думает...");
                String answer = chatgpt.sendMessage(prompt, userChatHistory);
                updateTextMessage(msg, answer);
                return;
            }
            list.add(message);
            return;
        }

        //command PROFILE
        if(message.equals("/profile")){
            currentMode = DialogMode.PROFILE;
            sendPhotoMessage("profile");
            String text = loadMessage("profile");
            sendTextMessage(text);
            me = new UserInfo();
            questionCount = 1;
            sendTextMessage("Сколько вам лет?");
            return;
        }
        if(currentMode == DialogMode.PROFILE && !isMessageCommand()){
            switch(questionCount){
                case 1:
                me.age = message;
                questionCount = 2;
                sendTextMessage("Кем вы работаете?");
                break;

                case 2:
                me.occupation = message;
                questionCount = 3;
                sendTextMessage("какое у вас хобби?");
                break;

                case 3:
                me.hobby = message;
                questionCount = 4;
                sendTextMessage("Что вам не нравится в людях?");
                break;

                case 4:
                    me.annoys = message;
                    questionCount = 5;
                    sendTextMessage("Цель знакомства?");
                    break;
                case 5:
                    me.goals = message;
                    String aboutMyself = me.toString();

                    Message msg = sendTextMessage("Подождите пару секунд, ChatGPT \uD83E\uDDE0 думает...");
                    String answer = chatgpt.sendMessage(loadPrompt("profile"), aboutMyself);
                    updateTextMessage(msg, answer);
                    break;
            }
        }

        //command OPENER
        if(message.equals("/opener")){
            currentMode = DialogMode.OPENER;
            sendPhotoMessage("opener");
            sendTextMessage(loadMessage("opener"));
            she = new UserInfo();
            questionCount = 1;
            sendTextMessage("Имя девушки");
            return;
        }

        if(currentMode == DialogMode.OPENER && !isMessageCommand()){
            switch (questionCount){
                case 1:
                    she.name = message;
                    questionCount = 2;
                    sendTextMessage("Сколько ей лет?");
                    break;
                case 2:
                    she.age = message;
                    questionCount = 3;
                    sendTextMessage("Какое у нее хобби?");
                    break;
                case 3:
                    she.hobby = message;
                    questionCount = 4;
                    sendTextMessage("Кем она работает?");
                    break;
                case 4:
                    she.occupation = message;
                    questionCount = 5;
                    sendTextMessage("Цель знакомства?");
                    break;
                case 5:
                    she.goals = message;
                    String aboutFriend = she.toString();


                    Message msg = sendTextMessage("Подождите пару секунд, ChatGPT \uD83E\uDDE0 думает...");
                    String answer = chatgpt.sendMessage(loadPrompt("opener"), aboutFriend);
                    updateTextMessage(msg, answer);
                    break;
            }

        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
