package com.example.beavi5.shvabrashvabr.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.beavi5.shvabrashvabr.adapters.RVPostAdapter;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**класс Model
 *Класс осуществляет загрузгу и хранение данных с RSS
 */

public class RSSItem  {
    String title;
    String desc;
    String author;
    String categories;

    public String getCategories() {
        return categories;
    }

    public String getAuthor() {
        return author;
    }

    String link;
    String pubDate;

    public RSSItem(String title, String desc,  String link, String pubDate, String author, String categories) {
        this.title = title;
        this.desc = desc;
        this.categories = categories;
        this.author = author;

        this.link = link;
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }



    public String getLink() {
        return link;
    }

    /**метод для парсинга данных RSS*/
    public static List<RSSItem> ParseRss(String feedUrl, final ArrayList<RSSItem> arrayListPosts, final RVPostAdapter rvPostAdapter){


        new myAsyncTask(arrayListPosts,rvPostAdapter).execute(feedUrl);
        return arrayListPosts;
    }


}


/** класс для загрузки данных RSS ленты в отдельном потоке*/
class myAsyncTask extends AsyncTask<String,List<RSSItem>,List<RSSItem>>{
    List<RSSItem> arrayListPosts;
    RVPostAdapter rvPostAdapter;
    public myAsyncTask(List<RSSItem> list,RVPostAdapter rvPostAdapter) {
        arrayListPosts=list;
        this.rvPostAdapter=rvPostAdapter;
    }

    @Override

    protected List<RSSItem> doInBackground(String... strings) {
        List<RSSItem> rssItems= new ArrayList<>();


        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){

                InputStream is = conn.getInputStream();


                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document= db.parse(is);
                Element element = document.getDocumentElement();
                NodeList nodeList = element.getElementsByTagName("item");

                if (nodeList.getLength()>0)
                {

                    for (int i = 0; i <nodeList.getLength() ; i++) {
                        Log.d("arr",""+nodeList.getLength());

                        Element entry = (Element) nodeList.item(i);

                        Element _title= (Element) entry.getElementsByTagName("title").item(0);
                        Element _pubDate= (Element) entry.getElementsByTagName("pubDate").item(0);
                        String pubDate= _pubDate.getFirstChild().getNodeValue();

                        Element _author= (Element) entry.getElementsByTagName("dc:creator").item(0);
                        String author= _author.getFirstChild().getNodeValue();


                        Element _link= (Element) entry.getElementsByTagName("link").item(0);
                        String title = _title.getFirstChild().getNodeValue();
                        String link = _link.getFirstChild().getNodeValue();

                        StringBuilder categgories= new StringBuilder();
                        NodeList nodeListCategories =  entry.getElementsByTagName("category");
                        for (int j = 0; j <nodeListCategories.getLength() ; j++) {
                            Element category = (Element) nodeListCategories.item(j);
                            categgories.append(category.getFirstChild().getNodeValue());
                            if (j!=nodeListCategories.getLength()-1) categgories.append(", ");
                        }


                        String desc="";

                        Element _desc = (Element) entry.getElementsByTagName("description").item(0);

                        NodeList list = _desc.getChildNodes();

                        for(int index = 0; index < list.getLength(); index++){
                            if(list.item(index) instanceof CharacterData){
                                CharacterData child = (CharacterData) list.item(index);
                                desc = child.getData();

                                if(desc != null && desc.trim().length() > 0){
                                    desc= child.getData();

                                    //задаем ширину всех картинок в описании

                                    desc = setWidthToAllImgs(desc);

                                    break;}
                            }
                        }


                        rssItems.add( new RSSItem(title, desc, link, pubDate, author, categgories.toString()));

                        publishProgress(rssItems);


                    }
                }




            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return rssItems;

    }

    /**
     * Метод присваивает всем html-картинкам ширину для корректного отображения на экране
     * @param desc
     * @return
     */
    private String setWidthToAllImgs(String desc) {
        desc = desc.replace("<img","<img width=\"100%\" ");
        return desc;
    }


    @Override
    protected void onProgressUpdate(List<RSSItem>... values) {
        super.onProgressUpdate(values);
        arrayListPosts.clear();
        arrayListPosts.addAll(values[0]);
        rvPostAdapter.notifyDataSetChanged();

    }

}