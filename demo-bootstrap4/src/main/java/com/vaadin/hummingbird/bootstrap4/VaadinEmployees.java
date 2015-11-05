package com.vaadin.hummingbird.bootstrap4;

import java.util.ArrayList;
import java.util.List;

public class VaadinEmployees {
    public static class Employee {
        private String name;
        private String imageUrl;
        private String title;

        public Employee(String imageUrl, String name, String title) {
            super();
            this.name = name;
            this.imageUrl = imageUrl;
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    public static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/joonas.png",
                "Joonas Lehtinen", "CEO, Founder"));

        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jurka.png",
                "Jurka Rahikkala", "COO, Founder"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jani.png",
                "Jani Laakso", "Project Manager, Founder"));
        employees.add(new Employee(
                "https://vaadin.com/image/image_gallery?uuid=9977b966-19c7-49d1-a8ae-4ee57ce934d3&groupId=10187&t=1360067298350",
                "Marc Englund", "Product Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/sami.png",
                "Sami Ekblad", "Product Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/henrim.jpg/5d957694-7025-49cd-a8ce-6ca0f5e1985e?t=1409139651906",
                "Henri Muurimaa", "SVP of Engineering"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/ville.png",
                "Ville Ingman", "Vaadin Advocate"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/mikael.png",
                "Mikael Vappula", "Infrastructure Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/tomivirtanen.png",
                "Tomi Virtanen", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/artur.png",
                "Artur Signell", "CTO"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jouni.png",
                "Jouni Koivuviita", "Design Director"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/matti.png",
                "Matti Tahvonen", "Sr. Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jonatan.png",
                "Jonatan Kronqvist", "VP of Products"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/hannu.png",
                "Hannu Salonen", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/hene.png",
                "Henri Kerola", "Sr. Vaadin Expert, Project Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/teemu.png",
                "Teemu Pöntelin", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/johannes.png",
                "Johannes Tuikkala", "Vaadin Expert, Project Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/risto.png",
                "Risto Yrjänä", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/kim.png",
                "Kim Leppänen", "Resourcing Manager, Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jens.png",
                "Jens Jansson", "Product Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/henrikpaul.jpg/d9d0da78-8e8c-4142-b0c9-6bb991ad38f3?t=1411976283985",
                "Henrik Paul", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/john.png",
                "John Ahlroos", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/thomas.png",
                "Thomas Mattsson", "Vaadin Expert, Project Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/fredu.png",
                "Fredrik Rönnlund", "VP of Marketing"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/teppo.png",
                "Teppo Kurki", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/hesara.png",
                "Henri Sara", "Sr. Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/sebastian.png",
                "Sebastian Nyholm", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/peter.png",
                "Peter Lehto", "Vaadin Expert, Project Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/virkki.png",
                "Tomi Virkki", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/mgrankvi.png",
                "Mikael Grankvist", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/petter.png",
                "Petter Holmström", "Vaadin Architect"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/marcushellberg.png",
                "Marcus Hellberg", "Sr. Vaadin Expert, Key Account Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/petri.png",
                "Petri Heinonen", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/mattivesa.png",
                "Matti Vesa", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/anna.png",
                "Anna Koskinen", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/marlon.jpg/fa9e4026-25ed-44d7-a4c8-477b8548d42e?t=1409139668340",
                "Marlon Richert", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jarno.png",
                "Jarno Rantala", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/magi.png",
                "Marko Grönroos", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/johku.png",
                "Johannes Häyry", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/leif.png",
                "Leif Åstrand", "Senior Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/samik.png",
                "Sami Kaksonen", "VP of Sales"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/j_dahlstrom.png",
                "Johannes Dahlström", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/samuli.png",
                "Samuli Penttilä", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/rofa.png",
                "Rolf Smeds", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/alump.png",
                "Sami Viitanen", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/pekka.png",
                "Pekka Hyvönen", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/tapio.png",
                "Tapio Aali", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/haijian.png",
                "Haijian Wang", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/miki.png",
                "Mikolaj Olszewski", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/tanja.png",
                "Tanja Repo", "Marketing Coordinator"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/pekka.perala.png",
                "Pekka Perälä", "Key Account Manager"));
        employees.add(new Employee(
                "https://vaadin.com/vaadin-theme/images/company/personnel/jonni.png",
                "Jonni Nakari", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/denis.png/195f599d-64f9-4c8e-96ea-32c9006ce447?t=1402995006000",
                "Denis Anisimov", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/image/image_gallery?uuid=36db0405-7d1e-41e5-b315-c42ffc38ef78&groupId=10187&t=1362992325210",
                "Amir Al-Majdalawi", "Key Account Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/partik.png/31546700-1685-497b-b8b2-7b75d154d9c0?t=1402995211000",
                "Patrik Lindström", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/michaelt.png/abe07768-d538-4995-b5a6-6780dc673923?t=1402995152000",
                "Michael Tzukanov", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/mattihosio.png/d4136ed9-7e7b-4c00-9f47-652a533e0ebe?t=1402995141000",
                "Matti Hosio", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/johanneseriksson.png/f3c7710a-0dc1-4999-acb8-aa0cc12fcc86?t=1402995074000",
                "Johannes Eriksson", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/joacim.png/7caceb96-3b5b-4629-8d4f-ed2b9905759d?t=1402995062000",
                "Joacim Päivärinne", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/felype.png/dd245057-78d7-4849-8eb7-b4a8d24b4aa2?t=1402995028000",
                "Felype Ferreira", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/mika.png/989dbd3e-de0a-4c4e-bcd9-f71bbc9c957a?t=1402995163000",
                "Mika Murtojärvi", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/artem.png/9f1a4ed1-d8e2-433e-bcc5-ba0c3e3bc477?t=1402994994000",
                "Artem Godin", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/maciej.png/6add5593-bd7b-4899-a40d-5a2cad4ae231?t=1402995107000",
                "Maciej Przepiora", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/teemus.png/d714cd8b-4efa-47cf-80f2-4a1d6e4115cc?t=1402995230000",
                "Teemu Suo-Anttila", "Vaadin Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/minna.png/e4b2dc1e-d5c0-4994-a17f-64b5f75c3e2e?t=1402995174000",
                "Minna Hohti", "Management Assistant"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/sauli.png/e3093d43-f520-41c0-bd72-492277df4e0d?t=1402995221000",
                "Sauli Tähkäpää", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/manolo.png/41987ded-9091-4dd6-b7f3-411c720839fa?t=1402995118000",
                "Manuel Carrasco", "Sr. GWT Expert"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/jarmo.png/1b32543e-71b5-422b-bf8f-49ebf14e9bba?t=1402995052000",
                "Jarmo Kemppainen", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/henrik.png/28627608-7693-4b5d-8d9c-6fbcd5a567f9?t=1402995040000",
                "Henrik Skogström", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/dmitrii.png/42eec4d3-2444-4599-a38c-cfb3cbb72b9b?t=1402995017000",
                "Dmitrii Rogozin", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/markus.png/32c8875e-3b60-4f34-8262-94c88fbd27df?t=1402995129000",
                "Markus Koivisto", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/bogdan.png/f090574d-f64d-4bf2-bfe5-66924446eb5a?t=1403872722223",
                "Bogdan Udrescu", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/guillermo.jpg/8aec29fd-ff4d-4986-9105-9f5cc84d8209?t=1409139610000",
                "Guillermo Alvarez", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/sergey.jpg/f02f4014-9df9-4407-8669-228ca480f2de?t=1411377814055",
                "Sergey Budkin", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/kari.jpg/5cf382f5-4288-44d8-83fd-f9304c08970c?t=1412253599160",
                "Kari Kaitanen", "Key Account Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/amahdy.jpg/2f3d7bae-d3fa-4550-9322-7306bcc5c8b3?t=1423037220491",
                "Amahdy Abdelaziz", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/murat.jpg/ddfddb13-3ade-4285-9f7f-0ebf8493f9f9?t=1423037268670",
                "Murat Yamak", "Sales Analyst"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/ville.jpg/c4c28348-3fb8-4b64-b857-56d0aa01180f?t=1423037244989",
                "Ville Saarinen", "Sales Analyst"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/enver.jpg/65fb6973-e608-478b-b852-af87264336b0?t=1423037285496",
                "Enver Haase", "Project Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/tatulund.jpg/8b82b276-f20f-42c7-b0b9-60754202b350?t=1429190384000",
                "Tatu Lund", "Maintenance Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/ollimurto.jpg/c5fe1b2b-feaa-4ea7-98e6-13fa6abc239b?t=1429190384000",
                "Olli Murto", "Key Account manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/ilyamotornyi.jpg/12255eb3-6382-4ae6-863f-285b87719f80?t=1429190384000",
                "Ilia Motornyi", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/alejandroduarte.jpg/1394aa34-de6e-43f0-988a-dd95601a3df7?t=1429190383000",
                "Alejandro Duarte", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/jurgentreml.jpg/db1ba24b-2fe3-4ace-9890-25384d4aa568?t=1434024436859",
                "Jürgen Treml", "Key Account Manager"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/ollitietavainen/dd84760a-9b73-42d4-ac44-9055e61bb870?t=1439363943637",
                "Olli Tietäväinen", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/tuomasnironen/ab0da305-31dc-4f62-994c-7c9bcc4de1a8?t=1439363916175",
                "Tuomas Nironen", "Vaadin Designer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/frederikraulf/dff623e1-6e55-48c7-8620-7100f1dea9b3?t=1441797084557",
                "Frederik Raulf", "Junior Sales Analyst"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/pontusbostrom/cdebbf67-a776-455f-bf4e-dce0ca9653b9?t=1441797101000",
                "Pontus Boström", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/antonplatonov/07c5842a-57c4-4fcd-836f-daf0eb5cc0bc?t=1445414770722",
                "Anton Platonov", "Vaadin Developer"));
        employees.add(new Employee(
                "https://vaadin.com/documents/10187/2497140/berndhopp/64d0da88-576c-4ecd-976b-ecb5f791fd13?t=1445414746262",
                "Bernd Hopp", "Vaadin Developer"));

    }
}
