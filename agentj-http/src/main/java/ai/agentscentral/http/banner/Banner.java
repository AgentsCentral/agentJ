package ai.agentscentral.http.banner;

/**
 * Banner
 *
 * @author Rizwan Idrees
 */
public class Banner {

    private Banner() {
    }

    private final static String banner = """
  
  
         █████╗  ██████╗ ███████╗███╗   ██╗████████╗  ██╗
        ██╔══██╗██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝  ██║
        ███████║██║  ███╗█████╗  ██╔██╗ ██║   ██║     ██║
        ██╔══██║██║   ██║██╔══╝  ██║╚██╗██║   ██║██   ██║
        ██║  ██║╚██████╔╝███████╗██║ ╚████║   ██║╚█████╔╝
        ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝ ╚════╝\s
                                                        \s
                                                                 
""";


    public static void printBanner(){
        System.out.println(banner);
    }

}
