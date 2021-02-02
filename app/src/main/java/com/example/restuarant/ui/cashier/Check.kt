package com.example.restuarant.ui.cashier


class Check(name: String, price: String) {
    val html = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "    <title>Document</title>" +
            "    <style>" +
            "        .check {" +
            "            max-width: 800px;" +
            "            margin: auto;" +
            "            font-family: Arial, Helvetica, sans-serif;" +
            "        }" +
            "        " +
            "        .check h3 {" +
            "            font-size: 25px;" +
            "            text-align: center;" +
            "        }" +
            "        " +
            "        section .box {" +
            "            display: flex;" +
            "            justify-content: space-between;" +
            "        }" +
            "        " +
            "        section .section_01," +
            "        section .section_02," +
            "        section .section_03 {" +
            "            border-bottom: 2px dashed black;" +
            "            padding: 10px 0;" +
            "        }" +
            "        " +
            "        section .section_01 div:last-of-type p," +
            "        section .section_02 div:last-of-type p," +
            "        section .section_03 div:last-of-type p {" +
            "            text-align: right;" +
            "        }" +
            "        " +
            "        section .section_01 div p," +
            "        section .section_02 div p," +
            "        section .section_03 div p {" +
            "            font-size: 18px;" +
            "            margin: 5px 0;" +
            "        }" +
            "        " +
            "        section .section_03 div p {" +
            "            font-weight: 600;" +
            "        }" +
            "        " +
            "        section .section_04 div {" +
            "            font-size: 18px;" +
            "            display: flex;" +
            "            justify-content: space-between;" +
            "        }" +
            "        " +
            "        @media(max-width: 490px) {" +
            "            .check h3 {" +
            "                font-size: 21px;" +
            "            }" +
            "            section .section_01 div p," +
            "            section .section_02 div p," +
            "            section .section_03 div p {" +
            "                font-size: 14px;" +
            "                margin: 5px 0;" +
            "            }" +
            "            section .section_04 div {" +
            "                font-size: 14px;" +
            "            }" +
            "        }" +
            "    </style>" +
            "</head>" +
            "<body>" +
            "    <div class=\"check\">" +
            "        <h3>\"Anglesey Food\" MCHJ XK</h3>" +
            "        <h3></h3>" +
            "        <section>" +
            "            <div class=\"box section_01\">" +
            "                <div>" +
            "                    <p class=\"stir\">STIR: 000202099756</p>" +
            "                    <p class=\"cashier\">KASSIR: Абдулазиз</p>" +
            "                    <p class=\"trade\">SAVDO CHEKI N 206</p>" +
            "                </div>" +
            "                <div>" +
            "                    <p class=\"stn\">S/N: STS-20200617-000170</p>" +
            "                    <p class=\"pos\">POS N 10</p>" +
            "                    <p class=\"date\">26/01/2021 22:46:39</p>" +
            "                </div>" +
            "            </div>" +
            "            <div class=\"box section_02\">" +
            "                <div>" +
            "                    $name" +
            "                </div>" +
            "                <div> $price </div>" +
            "            </div>" +
            "            <div class=\"box section_03\">" +
            "                <div>" +
            "                    <p class=\"all_price\">HAMMASI:</p>" +
            "                    <p class=\"payed\">TO'LANDI:</p>" +
            "                    <p class=\"return\">QAYTIM:</p>" +
            "                    <p class=\"form_pay\">TO'LOV SHAKLI:</p>" +
            "                </div>" +
            "                <div>" +
            "                    <p class=\"price_01\">29690,00 So'm</p>" +
            "                    <p class=\"price_03\">50700,00 So'm</p>" +
            "                    <p class=\"price_04\">21010,00 So'm</p>" +
            "                    <p class=\"price_05\">NAQD</p>" +
            "                </div>" +
            "            </div>" +
            "            <div class=\" section_04\">" +
            "                <h3>RAHMATI</h3>" +
            "                <div>" +
            "                    <span class=\"fm\">FM: UZ191211501570</span>" +
            "                    <span class=\"fb\">FB: 735194210554</span>" +
            "                </div>" +
            "            </div>" +
            "        </section>" +
            "    </div>" +
            "</body>" +
            "</html>"
}