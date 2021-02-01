package com.example.restuarant.ui.cashier.check

/**
 * Created by shohboz on 29,Январь,2021
 */

class CheckL (val name:String, val price:String){
    val html = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Document</title>\n" +
            "    <style>\n" +
            "        .check {\n" +
            "            max-width: 800px;\n" +
            "            margin: auto;\n" +
            "            font-family: Arial, Helvetica, sans-serif;\n" +
            "        }\n" +
            "        \n" +
            "        .check h3 {\n" +
            "            font-size: 25px;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        \n" +
            "        section .box {\n" +
            "            display: flex;\n" +
            "            justify-content: space-between;\n" +
            "        }\n" +
            "        \n" +
            "        section .section_01,\n" +
            "        section .section_02,\n" +
            "        section .section_03 {\n" +
            "            border-bottom: 2px dashed black;\n" +
            "            padding: 10px 0;\n" +
            "        }\n" +
            "        \n" +
            "        section .section_01 div:last-of-type p,\n" +
            "        section .section_02 div:last-of-type p,\n" +
            "        section .section_03 div:last-of-type p {\n" +
            "            text-align: right;\n" +
            "        }\n" +
            "        \n" +
            "        section .section_01 div p,\n" +
            "        section .section_02 div p,\n" +
            "        section .section_03 div p {\n" +
            "            font-size: 18px;\n" +
            "            margin: 5px 0;\n" +
            "        }\n" +
            "        \n" +
            "        section .section_03 div p {\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        \n" +
            "        section .section_04 div {\n" +
            "            font-size: 18px;\n" +
            "            display: flex;\n" +
            "            justify-content: space-between;\n" +
            "        }\n" +
            "        \n" +
            "        @media(max-width: 490px) {\n" +
            "            .check h3 {\n" +
            "                font-size: 21px;\n" +
            "            }\n" +
            "            section .section_01 div p,\n" +
            "            section .section_02 div p,\n" +
            "            section .section_03 div p {\n" +
            "                font-size: 14px;\n" +
            "                margin: 5px 0;\n" +
            "            }\n" +
            "            section .section_04 div {\n" +
            "                font-size: 14px;\n" +
            "            }\n" +
            "        }\n" +
            "        \n" +
            "        @media(max-width: 330px) {\n" +
            "            .check h3 {\n" +
            "                font-size: 10px;\n" +
            "            }\n" +
            "            section .section_01 div p,\n" +
            "            section .section_02 div p,\n" +
            "            section .section_03 div p {\n" +
            "                font-size: 8px;\n" +
            "                margin: 4px 0;\n" +
            "            }\n" +
            "            section .section_04 div {\n" +
            "                font-size: 8px;\n" +
            "            }\n" +
            "            section .section_01,\n" +
            "            section .section_02,\n" +
            "            section .section_03 {\n" +
            "                border-bottom: 2px dashed rgba(0, 0, 0, 0.479);\n" +
            "                padding: 7px 0;\n" +
            "            }\n" +
            "        }\n" +
            "        \n" +
            "        @media(max-width: 230px) {\n" +
            "            .check h3 {\n" +
            "                font-size: 10px;\n" +
            "            }\n" +
            "            section .section_01 div p,\n" +
            "            section .section_02 div p,\n" +
            "            section .section_03 div p {\n" +
            "                font-size: 6px;\n" +
            "                margin: 4px 0;\n" +
            "            }\n" +
            "            section .section_04 div {\n" +
            "                font-size: 6px;\n" +
            "            }\n" +
            "            section .section_01,\n" +
            "            section .section_02,\n" +
            "            section .section_03 {\n" +
            "                border-bottom: 2px dashed rgba(0, 0, 0, 0.479);\n" +
            "                padding: 3px 0;\n" +
            "            }\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "    <div class=\"check\">\n" +
            "        <h3>\"Anglesey Food\" MCHJ XK</h3>\n" +
            "        <h3></h3>\n" +
            "        <section>\n" +
            "            <div class=\"box section_01\">\n" +
            "                <div>\n" +
            "                    <p class=\"stir\">STIR: 000202099756</p>\n" +
            "                    <p class=\"cashier\">KASSIR: Абдулазиз</p>\n" +
            "                    <p class=\"trade\">SAVDO CHEKI N 206</p>\n" +
            "                </div>\n" +
            "                <div>\n" +
            "                    <p class=\"stn\">S/N: STS-20200617-000170</p>\n" +
            "                    <p class=\"pos\">POS N 10</p>\n" +
            "                    <p class=\"date\">26/01/2021 22:46:39</p>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"box section_02\">\n" +
            "                <div>\n" + name +
            "                </div>\n" +
            "                <div>\n" + price +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\"box section_03\">\n" +
            "                <div>\n" +
            "                    <p class=\"all_price\">HAMMASI:</p>\n" +
            "                    <p class=\"payed\">TO'LANDI:</p>\n" +
            "                    <p class=\"return\">QAYTIM:</p>\n" +
            "                    <p class=\"form_pay\">TO'LOV SHAKLI:</p>\n" +
            "                </div>\n" +
            "                <div>\n" +
            "                    <p class=\"price_01\">29690,00 So'm</p>\n" +
            "                    <p class=\"price_02\">3872,62 So'm</p>\n" +
            "                    <p class=\"price_03\">50700,00 So'm</p>\n" +
            "                    <p class=\"price_04\">21010,00 So'm</p>\n" +
            "                    <p class=\"price_05\">NAQD</p>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\" section_04\">\n" +
            "                <h3>RAHMATI</h3>\n" +
            "                <div>\n" +
            "                    <span class=\"fm\">FM: UZ191211501570</span>\n" +
            "                    <span class=\"fb\">FB: 735194210554</span>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </section>\n" +
            "    </div>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>"
}
