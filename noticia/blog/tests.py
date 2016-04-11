from django.test import TestCase
from django.utils import timezone
from django.core.urlresolvers import reverse
from .models import NewsItem, Event
from .views import NewsItemForm, EventForm
from rest_framework.test import APITestCase
from rest_framework import status


def news_example(title):
    ahora = timezone.now()
    return NewsItem.objects.create(title=title, description="prueba descripcion", publish_date=ahora)


def event_example(title):
    ahora = timezone.now()
    return Event.objects.create(title=title, description="prueba descripcion", start_date=ahora, end_date=ahora)


class NewsItemEventTest(TestCase):

    def test_index(self):
        resp = self.client.get(reverse('blog:index'))
        self.assertEqual(resp.status_code, 200)

    def test_noticia(self):
        resp = self.client.get(reverse('blog:noticias'))
        self.assertEqual(resp.status_code, 200)

    def test_evento(self):
        resp = self.client.get(reverse('blog:eventos'))
        self.assertEqual(resp.status_code, 200)

    def test_both(self):
        resp = self.client.get(reverse('blog:todo'))
        self.assertEqual(resp.status_code, 200)

    def test_class_form(self):
        data = {'title': "titulo", 'description': "descripcion", 'publish_date': timezone.now()}
        form = NewsItemForm(data=data)
        form.save()
        self.assertTrue(form.is_valid())
        x = NewsItem.objects.get(title="titulo")
        self.assertTrue(x)

    def test_v2detail(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:detailnews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)

    def test_v1detail(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:1detailnews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)

    def test_news_create_v1(self):
        resp = self.client.get(reverse('blog:1newnews'))
        self.assertContains(resp, 'Publicar')

    def test_news_create_v2(self):
        resp = self.client.get(reverse('blog:newnews'))
        self.assertContains(resp, 'Publicar')

    def test_news_edit_v1(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:v1editnews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)
        resp = self.client.get(reverse('blog:noticias'))
        self.assertEqual(resp.status_code, 200)

    def test_news_edit_v2(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:editnews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)

    def test_news_delete_v1(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:v1deletenews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)
        self.assertContains(resp, 'borrar')

    def test_news_delete_v2(self):
        w = news_example("prueba")
        resp = self.client.get(reverse('blog:deletenews', kwargs={'pk': w.id}))
        self.assertEqual(resp.status_code, 200)
        self.assertContains(resp, 'borrar')

    def test_eventdetail(self):
        z = event_example("prueba")
        resp = self.client.get(reverse('blog:detailevent', kwargs={'pk': z.id}))
        self.assertEqual(resp.status_code, 200)

    def test_eventcreate(self):
        resp = self.client.get(reverse('blog:newevent'))
        self.assertContains(resp, 'Publicar')

    def test_eventedit(self):
        z = event_example("prueba")
        resp = self.client.get(reverse('blog:editevent', kwargs={'pk': z.id}))
        self.assertEqual(resp.status_code, 200)

    def test_eventdelete(self):
        z = event_example("prueba")
        resp = self.client.get(reverse('blog:deleteevent', kwargs={'pk': z.id}))
        self.assertEqual(resp.status_code, 200)
        self.assertContains(resp, 'borrar')

""" API TEST """


class NewsItemListAPITestCase(APITestCase):


    def addNewsItem(self):
            form = NewsItemForm(data={'title': "titulo", 'description': "descripcion", 'publish_date': timezone.now()})
            form.is_valid()
            form.save()
            return NewsItem.objects.get(title='titulo')

    def test_create(self):
        url = reverse('blog:apiNewsItem')
        data = {'title': "prueba", 'description': "prueba", 'publish_date': timezone.now()}
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(NewsItem.objects.get().title, 'prueba')

    def test_read(self):
        response = self.client.get(reverse('blog:apiNewsItem'))
        self.assertEqual(response.status_code, 200)

    def test_update_delete(self):
        self.addNewsItem()
        x = NewsItem.objects.get(title='titulo')
        url = reverse('blog:apiDetailNewsItem', kwargs={'pk': x.id})
        self.assertEqual(response.status_code, 200)
        self.assertEqual(NewsItem.objects.get().title, 'titulo')


class EventAPITestCase(APITestCase):

    def addEvent(self):
        form = EventForm(data={'title': 'titulo', 'description': 'descripcion', 'start_date': timezone.now(), 'end_date': timezone.now()})
        form.is_valid()
        form.save()
        return Event.objects.get(title="titulo")

    def test_create(self):
        url = reverse('blog:apiEvent')
        data = {'title': 'prueba', 'description': 'prueba', 'start_date': timezone.now(), 'end_date': timezone.now()}
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Event.objects.get().title, 'prueba')

    def test_read(self):
        response = self.client.get(reverse('blog:apiEvent'))
        self.assertEqual(response.status_code, 200)

    def test_update_delete(self):
        self.addEvent()
        x = Event.objects.get(title='titulo')
        url = reverse('blog:apiDetailEvent', kwargs={'pk': x.id})
        self.assertEqual(response.status_code, 200)
        self.assertEqual(Event.objects.get().title, 'titulo')
