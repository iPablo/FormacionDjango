from django.test import TestCase
from .models import NewsItem, Event, BaseNews, Base
from django.utils import timezone
from django.core.urlresolvers import reverse
from .forms import NewsItemForm, EventForm
from rest_framework import status
from rest_framework.test import APITestCase, APIClient
from django.contrib.auth.models import User
# import ipdb; ipdb.set_trace()


class AdminTestCase(TestCase):
    """Class AdminTestCase"""

    def test_admin(self):
        response = self.client.get('/admin/login/')
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "Django administration")


class NewsItemListAPITestCase(APITestCase):
    """Class NewsItemListAPITestCase"""

    def addUser(self):
        User.objects.create_user(username='admin', password='passpass',
                                 is_superuser=True)
        return User.objects.get(username='admin')

    def addNewsItem(self):
        form = NewsItemForm(data={'title': 'test title',
                            'description': 'test description',
                                  'publish_date': timezone.now(),
                                  'owner': self.addUser().id})
        form.is_valid()
        form.save()
        return NewsItem.objects.get(title="test title")

    def test_create(self):
        """Compruba si la API crea correctamente"""
        user = self.addUser()
        c = APIClient()
        url = reverse('NoticiasEventos:apiNewsItem')
        data = {'title': 'prueba api', 'description': 'prueba api',
                'publish_date': timezone.now(), 'owner': user.id}
        c.force_authenticate(user=user)
        response = c.post(url, data, format='json')
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


class EventAPITestCase(APITestCase):
    """Class EventAPITestCase"""

    def addUser(self):
        User.objects.create_user(username='admin', password='passpass',
                                 is_superuser=True)
        return User.objects.get(username='admin')

    def addEvent(self):
        form = EventForm(data={'title': 'event title',
                                        'description': 'event description',
                                        'start_date': timezone.now(),
                                        'end_date': timezone.now(),
                                        'owner': self.addUser().id})
        form.is_valid()
        form.save()
        return Event.objects.get(title="event title")

    def test_create(self):
        '''Compruba si la API crea correctamente'''
        user = self.addUser()
        c = APIClient()
        url = reverse('NoticiasEventos:apiEvent')
        data = {'title': 'prueba api', 'description': 'prueba api',
                'start_date': timezone.now(), 'end_date': timezone.now(),
                'owner': user.id}
        c.force_authenticate(user=user)
        response = c.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Event.objects.count(), 1)
        self.assertEqual(Event.objects.get().title, 'prueba api')

    def test_read(self):
        """Comprueba si la API muestra los objetos perfectamente"""
        response = self.client.get(reverse('NoticiasEventos:apiEvent'))
        self.assertEqual(response.status_code, 200)

    def test_update_delete(self):
        """Comprueba si la API muestra la vista para borrar y actualizar"""
        self.addEvent()
        x = Event.objects.get(title='event title')
        url = reverse('NoticiasEventos:apiEventDetail', kwargs={
            'pk': x.id})
        response = self.client.get(url, format='json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(Event.objects.count(), 1)
        self.assertEqual(Event.objects.get().title, 'event title')


class NewsItemTestCase(TestCase):
    """Class NewsItemTestCase"""

    def addUser(self):
        return User.objects.create_user(username='admin', password='passpass',
                                        is_superuser=True)

    def addNewsItem(self):
        return NewsItem.objects.create(title="test title",
                                       description="test description",
                                       publish_date=timezone.now(),
                                       owner=self.addUser())

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

    def test_index_v2List(self):
        '''Comprueba el acceso a v2List'''
        response = self.client.get(reverse('NoticiasEventos:v2List'))
        self.assertEqual(response.status_code, 200)
        self.assertContains(response, "No items found.")
        self.assertQuerysetEqual(response.context['result'], [])

    def test_v1Create(self):
        '''Comprueba la creacion del objeto'''
        antes = NewsItem.objects.count()
        resp_get = self.client.get('/v1/Create/')
        self.assertEqual(resp_get.status_code, 200)

        usuario = self.addUser()
        resp = self.client.post('/v1/Create/',
                                {'title': 'test title',
                                 'description': 'test description',
                                 'publish_date': '2016-04-15 10:00',
                                 'owner': usuario.id})
        self.assertEqual(resp.status_code, 302)
        despues = NewsItem.objects.count()
        self.assertEqual(antes, despues - 1)

    def test_v2Create(self):
        '''Comprueba la creacion del objeto'''
        antes = NewsItem.objects.count()
        resp_get = self.client.get('/v2/Create/')
        self.assertEqual(resp_get.status_code, 200)

        usuario = self.addUser()
        resp = self.client.post('/v1/Create/',
                                {'title': 'test title',
                                 'description': 'test description',
                                 'publish_date': '2016-04-15 10:00',
                                 'owner': usuario.id})
        self.assertEqual(resp.status_code, 302)
        despues = NewsItem.objects.count()
        self.assertEqual(antes, despues - 1)

    def test_v1Update(self):
        '''Comprueba la actualizacion del objeto'''
        aux = self.addNewsItem()
        resp_get = self.client.get(reverse('NoticiasEventos:v1Update',
                                           kwargs={'pk': aux.pk}))
        self.assertEqual(resp_get.status_code, 200)
        resp_post = self.client.post('/v1/Update/' + str(aux.pk) + '/',
                                     {'title': 'cambio',
                                      'description': 'test description',
                                      'publish_date': str(timezone.now())})
        self.assertEqual(resp_post.status_code, 302)

    def test_v2Update(self):
        '''Comprueba la actualizacion del objeto'''
        aux = self.addNewsItem()
        resp_get = self.client.get(reverse('NoticiasEventos:v2Update',
                                           kwargs={'pk': aux.pk}))
        self.assertEqual(resp_get.status_code, 200)
        resp_post = self.client.post('/v2/Update/' + str(aux.pk) + '/',
                                     {'title': 'cambio',
                                      'description': 'test description',
                                      'publish_date': '2015-10-10 10:00',
                                      'owner': 1})
        title = NewsItem.objects.get(id=aux.pk).title
        self.assertEqual(resp_post.status_code, 302)
        self.assertEqual(title, 'cambio')

    def test_v1Delete(self):
        '''Comprueba si se borra correctamente'''
        num_elem_before = NewsItem.objects.count()
        aux = self.addNewsItem()
        resp = self.client.get(reverse('NoticiasEventos:v1Delete',
                                       kwargs={'pk': aux.pk}))
        self.assertEqual(resp.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)

    def test_v2Delete(self):
        '''Comprueba si se borra correctamente'''
        num_elem_before = NewsItem.objects.count()
        aux = self.addNewsItem()
        resp_get = self.client.get(reverse('NoticiasEventos:v2Delete',
                                           kwargs={'pk': aux.pk}))
        self.assertEqual(resp_get.status_code, 200)
        resp_post = self.client.post(reverse('NoticiasEventos:v2Delete',
                                             kwargs={'pk': aux.pk}))
        self.assertEqual(resp_post.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)


class EventTestCase(TestCase):
    """Class EventTestCase"""

    def addUser(self):
        return User.objects.create_user(username='admin', password='passpass',
                                        is_superuser=True)

    def addEvent(self):
        return Event.objects.create(title='event title',
                                    description='event description',
                                    owner=self.addUser())

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

    def test_createEvent(self):
        '''Comprueba la creacion del objeto'''
        antes = Event.objects.count()
        resp_get = self.client.get('/event/Create/')
        self.assertEqual(resp_get.status_code, 200)

        usuario = self.addUser()
        resp = self.client.post('/event/Create/',
                                {'title': 'test title',
                                 'description': 'test description',
                                 'owner': usuario.id})
        self.assertEqual(resp.status_code, 302)
        despues = Event.objects.count()
        self.assertEqual(antes, despues - 1)

    def test_updateEvent(self):
        '''Comprueba la actualizacion del objeto'''
        aux = self.addEvent()
        resp_get = self.client.get(reverse('NoticiasEventos:eventUpdate',
                                           kwargs={'pk': aux.pk}))
        self.assertEqual(resp_get.status_code, 200)
        resp_post = self.client.post('/event/Update/' + str(aux.pk) + '/',
                                     {'title': 'cambio',
                                      'description': 'test description',
                                      'owner': 1})
        title = Event.objects.get(id=aux.pk).title
        self.assertEqual(resp_post.status_code, 302)
        self.assertEqual(title, 'cambio')

    def test_deleteEvent(self):
        '''Comprueba si se borra correctamente'''
        num_elem_before = Event.objects.count()
        aux = self.addEvent()
        resp_get = self.client.get(reverse('NoticiasEventos:eventDelete',
                                           kwargs={'pk': aux.pk}))
        self.assertEqual(resp_get.status_code, 200)
        # import ipdb; ipdb.set_trace()
        resp_post = self.client.post(reverse('NoticiasEventos:eventDelete',
                                             kwargs={'pk': aux.pk}))
        self.assertEqual(resp_post.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)


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
