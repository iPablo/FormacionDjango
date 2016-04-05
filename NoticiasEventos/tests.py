from django.test import TestCase
from .models import NewsItem, Event, BaseNews, Base
from django.utils import timezone
from django.core.urlresolvers import reverse
from .forms import NewsItemForm, EventForm
from rest_framework import status
from rest_framework.test import APITestCase
# import ipdb; ipdb.set_trace()


class AdminTestCase(TestCase):
    """Class AdminTestCase"""

    def test_admin(self):
        response = self.client.get('/admin/login/')
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "Django administration")


class NewsItemListAPITestCase(APITestCase):
    """Class NewsItemListAPITestCase"""

    def addNewsItem(self):
        form = NewsItemForm(data={'title': 'test title',
                            'description': 'test description',
                                  'publish_date': timezone.now()})
        form.is_valid()
        form.save()
        return NewsItem.objects.get(title="test title")

    def test_create(self):
        """Compruba si la API crea correctamente"""
        url = reverse('NoticiasEventos:apiNewsItem')
        data = {'title': 'prueba api', 'description': 'prueba api',
                'publish_date': timezone.now()}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(NewsItem.objects.count(), 1)
        self.assertEqual(NewsItem.objects.get().title, 'prueba api')

    def test_read(self):
        """Comprueba si la API muestra los objetos perfectamente"""
        response = self.client.get(reverse('NoticiasEventos:apiNewsItem'))
        self.assertEqual(response.status_code, 200)

    def test_update_delete(self):
        """Comprueba si la API muestra la vista para borrar y actualizar"""
        self.addNewsItem()
        x = NewsItem.objects.get(title='test title')
        url = reverse('NoticiasEventos:apiNewsItemDetail', kwargs={
            'pk': x.id})
        response = self.client.get(url, format='json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(NewsItem.objects.count(), 1)
        self.assertEqual(NewsItem.objects.get().title, 'test title')


class NewsItemTestCase(TestCase):
    """Class NewsItemTestCase"""

    def addNewsItem(self):
        form = NewsItemForm(data={'title': 'test title',
                            'description': 'test description',
                                  'publish_date': timezone.now()})
        form.is_valid()
        form.save()
        return NewsItem.objects.get(title="test title")

    def test_index(self):
        '''Comprueba el acceso a index'''
        response = self.client.get(reverse('NoticiasEventos:index'))
        self.assertEqual(response.status_code, 200)

    def test_str(self):
        x = NewsItem(title="prueba")
        self.assertEqual(str(x), "Title: " + x.title)

    def test_unicode(self):
        x = NewsItem(title="prueba")
        self.assertEqual(unicode(x), "Title: " + x.title)

    def test_index_v1List(self):
        '''Comprueba el acceso a v1List'''
        response = self.client.get(reverse('NoticiasEventos:v1List'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No items found.")
        self.assertQuerysetEqual(response.context['result'], [])

    def test_index_v2List(self):
        '''Comprueba el acceso a v2List'''
        response = self.client.get(reverse('NoticiasEventos:v2List'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No items found.")
        self.assertQuerysetEqual(response.context['result'], [])

    def test_form_create_newsItem(self):
        '''Comprueba la validacion del formulario y la creacion del objeto'''
        form = NewsItemForm(data={'title': 'prueba title',
                            'description': 'prueba description',
                                  'publish_date': timezone.now()})
        self.assertTrue(form.is_valid())
        form.save()
        x = NewsItem.objects.get(title="prueba title")
        self.assertTrue(x)

    def test_form_create_V1(self):
        '''Comprueba el acceso a v1Create'''
        response = self.client.get(reverse('NoticiasEventos:v1Create'))
        self.assertEqual(response.status_code, 200)

    def test_form_create_V2(self):
        '''Comprueba el acceso a v2Create'''
        response = self.client.get(reverse('NoticiasEventos:v2Create'))
        self.assertEqual(response.status_code, 200)

    def test_form_update_newsItemV1(self):
        '''Comprueba el acceso a v1Update'''
        x = self.addNewsItem()
        response = self.client.get(reverse('NoticiasEventos:v1Update', kwargs={
            'pk': x.id}))
        self.assertEqual(response.status_code, 200)

    def test_form_update_newsItemV2(self):
        '''Comprueba el acceso a v2Update'''
        x = self.addNewsItem()
        response = self.client.get(reverse('NoticiasEventos:v2Update', kwargs={
            'pk': x.id}))
        self.assertEqual(response.status_code, 200)

    def test_delete_v1(self):
        '''Comprueba si se borra correctamente'''
        x = self.addNewsItem()
        self.client.get(reverse('NoticiasEventos:v1Delete', kwargs={
            'pk': x.id}))
        self.assertQuerysetEqual(NewsItem.objects.all(), [])

    def test_delete_v2(self):
        '''Comprueba v2Delete'''
        x = self.addNewsItem()
        response = self.client.get(reverse('NoticiasEventos:v2Delete', kwargs={
            'pk': x.id}))
        self.assertContains(response, "deseas borrar la noticia")


class EventTestCase(TestCase):
    """Class EventTestCase"""

    def addEvent(self):
        form = EventForm(data={'title': 'event title',
                                        'description': 'event description',
                                        'start_date': timezone.now(),
                                        'end_date': timezone.now()})
        form.is_valid()
        form.save()
        return Event.objects.get(title="event title")

    def test_str(self):
        x = Event(title="prueba")
        self.assertEqual(str(x), "Title: " + x.title)

    def test_unicode(self):
        x = Event(title="prueba")
        self.assertEqual(unicode(x), "Title: " + x.title)

    def test_index_event(self):
        '''Comprueba el acceso a eventList'''
        response = self.client.get(reverse('NoticiasEventos:eventList'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No items found.")
        self.assertQuerysetEqual(response.context['result'], [])

    def test_form_create_event(self):
        '''Comprueba la validacion del formulario y la creacion del objeto'''
        form = EventForm(data={'title': 'simplePrueba',
                                        'description': 'simplePrueba',
                                        'start_date': timezone.now(),
                                        'end_date': timezone.now()})
        self.assertTrue(form.is_valid())
        form.save()
        x = Event.objects.get(title="simplePrueba")
        self.assertTrue(x)

    def test_create_event(self):
        '''Comprueba el acceso a eventCreate'''
        response = self.client.get(reverse('NoticiasEventos:v1Create'))
        self.assertEqual(response.status_code, 200)

    def test_update_event(self):
        '''Comprueba el acceso a eventUpdate'''
        x = self.addEvent()
        response = self.client.get(reverse('NoticiasEventos:eventUpdate',
                                   kwargs={'pk': x.id}))
        self.assertEqual(response.status_code, 200)

    def test_delete_event(self):
        '''Comprueba Delete Event'''
        x = self.addEvent()
        response = self.client.get(reverse('NoticiasEventos:eventDelete',
                                   kwargs={'pk': x.id}))
        self.assertContains(response, "deseas borrar el evento")


class BaseNewsTestCase(TestCase):
        """Class BaseNews"""

        def test_str(self):
            x = BaseNews(description="prueba")
            self.assertEqual(str(x), x.description)

        def test_unicode(self):
            x = BaseNews(description="prueba")
            self.assertEqual(unicode(x), x.description)


class BaseTestCase(TestCase):
        """Class Base"""

        def test_str(self):
            x = Base(title="prueba")
            self.assertEqual(str(x), x.title)

        def test_unicode(self):
            x = Base(title="prueba")
            self.assertEqual(unicode(x), x.title)
