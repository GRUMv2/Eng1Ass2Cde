package io.github.GRUMv2.EngSim.Server.EventHandler;

        import java.util.ArrayList;
import java.util.List;
        import java.util.Random;

public class ChristmasJokeEvent extends Event {
    private Random random = new Random();
    private boolean isDecember = false;
    private List<String> jokes = new ArrayList<>();


    ChristmasJokeEvent () {
        super();
        jokes.add("How did the ornament get addicted to Christmas?\nHe was hooked on trees his whole life.");
        jokes.add("What would you call an elf who just has won the lottery?\nWelfy!");
        jokes.add("What beats his chest and swings from Christmas cake to Christmas cake?\nTarzipan!");
        jokes.add("What's the difference between Santa Clause and a knight?\nOne slays a dragon, the other drags a sleigh!");
        jokes.add("Why are mummies such big fans of Christmas?\nBecause they enjoy wrapping.");
        jokes.add("What do you call a kid who doesn't believe in Santa?\nA rebel without a Claus.");
        jokes.add("What do you call an old snowman?\nWater.");
        jokes.add("What's the difference between Batman and the Grinch?\nBatman can go into Whoville without Robin.");
        jokes.add("Why did Mary and Joseph have to travel to Bethlehem?\nBecause they couldn't book a home delivery.");
        jokes.add("What do you call a penguin in the Sahara desert?\nLost.");
        jokes.add("Why has Santa been banned from sooty chimneys?\nCarbon footprints.");
        jokes.add("Which of Santa's reindeer has the best moves?\nDancer!");
        jokes.add("Why was the turkey in the pop group?\nBecause he was the only one with drumsticks!");
        jokes.add("What do you get if you put a bell on a skunk?\nJingle smells.");
        jokes.add("I got a Christmas card full of rice in the post today...\nI think it was from my Uncle Ben.");
        jokes.add("How did Darth Vader know what Luke Skywalker got for Christmas?\nHe felt his presents.");
        jokes.add("Why don't you ever see Father Christmas in hospital?\nBecause he has private elf care.");
        jokes.add("Why did the Grinch go to Bargain Booze?\nHe was searching for some holiday spirit.");
        jokes.add("What does Santa spend his wages on?\nJingle Bills.");
        jokes.add("What is white and minty?\nA polo bear!");
        jokes.add("What do elves do after school?\nTheir gnome work!");
        jokes.add("What falls at the North Pole but never gets hurt?\nSnow.");
        jokes.add("What was the three wise men's favourite Christmas carol?\nOh Camel, Ye Faithful.");
        jokes.add("What happened to the burglar who robbed an advent calendar factory?\nHe got 25 days.");
        jokes.add("What do sheep say to each other at Christmas time?\nMerry Christmas to ewe!");
        jokes.add("What comes at the end of Christmas Day?\nThe letter \"Y\"!");
        jokes.add("When is a Christmas dinner bad for your health?\nWhen you're the turkey...");
        jokes.add("What's every parent's favorite Christmas Carol?\nSilent Night.");
        jokes.add("What did the farmer get for Christmas?\nA cowculator.");
        jokes.add("What can you call a polar bear which wears ear muffs?\nAnything you want. He can't hear you!");
        jokes.add("What did one Christmas light say to the other Christmas light?\nYou light me up!");
        jokes.add("What did Santa do when he went speed dating?\nHe pulled a cracker.");
        jokes.add("How does Darth Vader enjoy his Christmas Turkey?\nOn the dark side!");
        jokes.add("What is the Grinch's least favourite band?\nThe Who!");
        jokes.add("How is Drake like an elf?\nHe spends all his time wrapping.");
        jokes.add("What do you get if Santa forgets to wear his undercrackers?\nSt Nickerless.");
        jokes.add("Why is it getting harder to buy Advent calendars?\nBecause their days are numbered!");
        jokes.add("What's a dog's favourite carol?\nBark, the herald angels sing.");
        jokes.add("What do you get if you cross Santa with a duck?\nA Christmas Quacker!");
        jokes.add("Who's Rudolph's favourite pop star?\nBeyon-sleigh!");
        jokes.add("Why is Parliament like ancient Bethlehem?\nIt takes a miracle to find three wise men there.");
        jokes.add("Who hides in the bakery at Christmas?\nA mince spy.");
        jokes.add("Which Christmas carol is about an animal with three legs?\nLittle Wonkey.");
        jokes.add("What do you call a snowman who goes on Love Island?\nA melt.");
        jokes.add("Where do elves go to dance?\nChristmas Balls.");
        jokes.add("What are the best Christmas sweaters made from?\nFleece Navidad!");
        jokes.add("What do the elves call it when Father Christmas claps his hands at the end of a play?\nSantapplause.");
        jokes.add("What is Santa's dogs name?\nSanta Paws!");
        jokes.add("How did Mary and Joseph know Jesus' weight when he was born?\nThey had a weigh in a manger!");
        jokes.add("How do snowmen get around?\nThey ride an icicle!");
        jokes.add("What do snowmen eat for lunch?\nIcebergers!");
        jokes.add("What do you sing at a Snowman's party?\nFreeze a jolly good fellow.");
        jokes.add("Why did the turkey cross the road?\nBecause it was the chicken's day off!");
        jokes.add("What do you call a snowman with a six pack?\nAn abdominal snowman.");
        jokes.add("Why did no-one bid for Rudolph and Dasher on eBay?\nBecause they were two deer.");
        jokes.add("What do reindeer hang on their Christmas trees?\nHorn-aments!");
        jokes.add("What did the snowman say to the robin?\nI have snow idea!");
        jokes.add("What do snowmen wear on their heads?\nIce caps.");
        jokes.add("Why was the snowman looking through the carrots?\nHe was picking his nose.");
        jokes.add("What did Adam say the day before Christmas?\n\"It's Christmas, Eve.\"");
        jokes.add("What do snowmen have for breakfast?\nSnowflakes.");
        jokes.add("What does Father Christmas do when his elves misbehave?\nHe gives them the sack.");
        jokes.add("Why couldn't the skeleton go to the Christmas party?\nHe had no body to go with.");
        jokes.add("Why did the turkey cross the road?\nBecause he wasn't chicken.");
        jokes.add("Why is Mrs Claus always checking Santa's phone?\nHe seems to know where all the naughty girls live.");
        jokes.add("What do you call an obnoxious reindeer?\nRude-olph!");
        jokes.add("Where would you find snowmen dancing?\nAt a snowball.");
        jokes.add("How did Scrooge win the football game?\nThe ghost of Christmas passed.");
        jokes.add("Where do snowmen keep their money?\nIn a snowbank.");
        jokes.add("Who do Santa's helpers call when they're ill?\nThe National Elf Service!");
        jokes.add("Why did Santa quit smoking?\nBecause it was bad for his elf.");
        jokes.add("What's Tarzan's favourite Christmas song?\nJungle bells.");
        jokes.add("What do you get when you cross a snowman and a vampire?\nFrostbite.");
        jokes.add("What does Prince George play at Christmas instead of musical chairs?\nGame of Thrones.");
        jokes.add("Which Christmas carol do dogs like best?\nBark the Herald Angels Sing!");
        jokes.add("Why does Santa go down the chimney on Christmas Eve?\nBecause it soots him!");
        jokes.add("Who says \"Oh, oh, oh\"?\nSanta walking backwards!");
        jokes.add("How can you keep your home warm this Christmas?\nTinsulation.");
        jokes.add("What do you get if you eat Christmas decorations?\nTinselitis.");
        jokes.add("What's every elf's favorite type of music?\nWrap.");
        jokes.add("Why can't the Christmas tree stand up?\nIt doesn't have legs.");
        jokes.add("Who is Santa's favourite actor?\nWillem Dafoe-ho-ho.");
        jokes.add("Why does your nose get tired in winter?\nIt runs all day.");
        jokes.add("Who tells the best Christmas jokes?\nReindeer. They sleigh every time.");
        jokes.add("What do you call Santa when he takes a break?\nSanta Pause.");
        jokes.add("Where do you find reindeer?\nIt depends on where you leave them!");
        jokes.add("Who is a Christmas tree's favorite singer?\nSpruce Springsteen.");
        jokes.add("What do you get if Santa goes down the chimney when a fire is lit?\nCrisp Kringle.");
        jokes.add("Why is the turkey never hungry at Christmas?\nIt's stuffed.");
        jokes.add("What does Santa use to bake cakes?\nElf-raising flour.");
        jokes.add("Why did the choir have to cancel their carol concert?\nThey caught tinsel-itis.");
        jokes.add("What is the duck's favourite Christmas carol?\nIn The Beak Midwinter.");
        jokes.add("Why didn't Mary and Joseph make it to Bethlehem?\nAll Virgin flights were cancelled.");
        jokes.add("Why are Santa's reindeer allowed to travel on Christmas Eve?\nThey have herd immunity.");
        jokes.add("Why is it best to think of 2023 like a panto?\nBecause eventually, it's behind you.");
        jokes.add("What do Santa's little helpers learn at school?\nThe elf-abet.");
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (broker.getTimeLeftString().contains("Dec") && !isDecember) {
            isDecember = true;
            server.popupManager.addPopup("Christmas Joke", jokes.get(random.nextInt(jokes.size())));
        } else if (!broker.getTimeLeftString().contains("Dec")) {
            isDecember = false;
        }

        return true;
    }
}
