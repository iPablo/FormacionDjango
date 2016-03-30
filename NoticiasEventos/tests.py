from django.test import TestCase
from .models import NewsItem, Event
from django.utils import timezone
from django.core.urlresolvers import reverse
from django.test.client import Client
from .forms import NewsItemForm, EventForm
import ipdb

# import ipdb; ipdb.set_trace()

class NewsItemTestCase(TestCase):

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
		'''Comprueba la validacion del formulario y la creacion del objeto NewsItem'''
		form = NewsItemForm(data={'title': 'prueba title', 
			'description': 'prueba description', 
			'publish_date': timezone.now()})
		self.assertTrue(form.is_valid())
		form.save()
		x = NewsItem.objects.get(title="prueba title")
		self.assertTrue(x)

	def test_form_update_newsItemV1(self):
		'''Comprueba el acceso a v1Update'''
		x = self.addNewsItem()
		response = self.client.get(reverse('NoticiasEventos:v1Update', kwargs = {'pk': x.id}))
		self.assertEqual(response.status_code, 200)

	def test_form_update_newsItemV2(self):
		'''Comprueba el acceso a v2Update'''
		x = self.addNewsItem()
		response = self.client.get(reverse('NoticiasEventos:v2Update', kwargs = {'pk': x.id}))
		self.assertEqual(response.status_code, 200)

	def test_delete_v1(self):
		'''Comprueba si se borra correctamente'''
		x = self.addNewsItem()
		self.client.get(reverse('NoticiasEventos:v1Delete', kwargs = {'pk':x.id}))
		self.assertQuerysetEqual(NewsItem.objects.all(), [])

class EventTestCase(TestCase):
	def addEvent(self):
		form = EventForm(data={'title': 'event title', 
			'description': 'event description', 
			'start_date': timezone.now(),
			'end_date': timezone.now()})
		form.is_valid()
		form.save()
		return Event.objects.get(title="event title")

	def test_index_event(self):
		'''Comprueba el acceso a eventList'''
		response = self.client.get(reverse('NoticiasEventos:eventList'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No items found.")
		self.assertQuerysetEqual(response.context['result'], [])

	def test_form_create_event(self):
		'''Comprueba la validacion del formulario y la creacion del objeto Event'''
		form = EventForm(data={'title': 'simplePrueba', 
			'description': 'simplePrueba', 
			'start_date': timezone.now(),
			'end_date': timezone.now()})
		self.assertTrue(form.is_valid())
		form.save()
		x = Event.objects.get(title="simplePrueba")
		self.assertTrue(x)
	
	def test_form_update_event(self):
		'''Comprueba el acceso a eventUpdate'''
		x = self.addEvent()
		response = self.client.get(reverse('NoticiasEventos:eventUpdate', kwargs = {'pk': x.id}))
		self.assertEqual(response.status_code, 200)

		
