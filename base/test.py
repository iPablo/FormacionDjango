#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.test import TestCase

from .models import NewsItem, Event
from .forms import EventsForm, NewsForm
from django.core.urlresolvers import reverse
from django.test.client import Client
from rest_framework.test import APIClient
from django.utils import timezone
from datetime import datetime, timedelta
from base.admin import admin
from django.contrib.admin.sites import AdminSite



class NewsItemTests(TestCase):

    def setUp(self):
        NewsItem.objects.create(title="lion", description="roar")

    def test_index(self):
        response = self.client.get(reverse("base:index"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_clases(self):
        response = self.client.get(reverse("base:TodosList"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_funciones(self):
        response = self.client.get(reverse("base:obtener_todo"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_clases(self):
        response = self.client.get(reverse("base:TodosList"))
        self.assertEqual(response.status_code, 200)

    def test_index_news(self):
        response = self.client.get(reverse("base:obtener_news"))
        self.assertEqual(response.status_code, 200)

    def test_detail_news_funciones(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:detail_news", kwargs={"new_id": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)

    def test_detail_news_clases(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:NewsItemDetail", kwargs={"pk": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)

    def test_create_post_funciones(self):
        num_elem_before = NewsItem.objects.count()
        url = reverse("base:create_new")
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before + 1)

    def test_create_post_clases(self):
        num_elem_before = NewsItem.objects.count()
        url = reverse("base:NewsCreate")
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before + 1)

    def test_update_post_funciones(self):
        num_elem_before = NewsItem.objects.count()
        nuevaNew = NewsItem.objects.get(title="lion")
        url = reverse("base:update_new", kwargs={"new_id": nuevaNew.pk})
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)

    def test_update_post_clases(self):
        num_elem_before = NewsItem.objects.count()
        nuevaNew = NewsItem.objects.get(title="lion")
        url = reverse("base:NewsUpdate", kwargs={"pk": nuevaNew.pk})
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)

    def test_news_delete_funciones(self):
        num_elem_before = NewsItem.objects.count()
        noticia = NewsItem.objects.get(pk=1)
        resp = self.client.get(reverse('base:delete_new', kwargs={'new_id': noticia.pk}))
        self.assertEqual(resp.status_code, 200)
        resp2 = self.client.post(reverse('base:delete_new', kwargs={'new_id': noticia.pk}))
        self.assertEqual(resp2.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before - 1)

    def test_news_delete_clases(self):
        num_elem_before = NewsItem.objects.count()
        noticia = NewsItem.objects.get(pk=1)
        resp = self.client.get(reverse('base:NewsDelete', kwargs={'pk': noticia.pk}))
        self.assertEqual(resp.status_code, 200)
        resp2 = self.client.post(reverse('base:NewsDelete', kwargs={'pk': noticia.pk}))
        self.assertEqual(resp2.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before - 1)


class EventTest(TestCase):

    def setUp(self):
        start_date = timezone.now()
        end_date = timezone.now()
        Event.objects.create(title="cat", description="meow", start_date=start_date, end_date=end_date)

    def test_index_events(self):
        response = self.client.get(reverse("base:obtener_events"))
        self.assertEqual(response.status_code, 200)

    def test_detail_events_funciones(self):
        nuevo = Event.objects.get(title="cat")
        response = self.client.get(reverse("base:detail_events", kwargs={"event_id": nuevo.pk}))
        self.assertEqual(response.status_code, 200)

    def test_create_post_funciones(self):
        date = timezone.now()
        month = str(date.month)
        day = str(date.day)
        year = str(date.year)
        num_elem_before = Event.objects.count()
        url = reverse("base:create_event")
        data = {"title": "jhbdvf",
                "description": "kjbfv",
                "start_date_month": month,
                "start_date_day": day,
                "start_date_year": year,
                "end_date_month": month,
                "end_date_day": day,
                "end_date_year": year}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before + 1)

    def test_create_post_clases(self):
        date = timezone.now()
        month = str(date.month)
        day = str(date.day)
        year = str(date.year)
        num_elem_before = Event.objects.count()
        url = reverse("base:EventsCreate")
        data = {"title": "jhbdvf",
                "description": "kjbfv",
                "start_date_month": month,
                "start_date_day": day,
                "start_date_year": year,
                "end_date_month": month,
                "end_date_day": day,
                "end_date_year": year}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before + 1)

    def test_update_post_funciones(self):
        num_elem_before = Event.objects.count()
        nuevoEvent = Event.objects.get(title="cat")
        date = timezone.now()
        month = str(date.month)
        day = str(date.day)
        year = str(date.year)
        url = reverse("base:update_event", kwargs={"event_id": nuevoEvent.pk})
        data = {"title": "jhbdvf",
                "description": "kjbfv",
                "start_date_month": month,
                "start_date_day": day,
                "start_date_year": year,
                "end_date_month": month,
                "end_date_day": day,
                "end_date_year": year}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)

    def test_update_post_clases(self):
        num_elem_before = Event.objects.count()
        nuevoEvent = Event.objects.get(title="cat")
        date = timezone.now()
        month = str(date.month)
        day = str(date.day)
        year = str(date.year)
        url = reverse("base:EventsUpdate", kwargs={"pk": nuevoEvent.pk})
        data = {"title": "jhbdvf",
                "description": "kjbfv",
                "start_date_month": month,
                "start_date_day": day,
                "start_date_year": year,
                "end_date_month": month,
                "end_date_day": day,
                "end_date_year": year}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before)

    def test_events_delete_funciones(self):
        num_elem_before = Event.objects.count()
        evento = Event.objects.get(pk=1)
        resp = self.client.get(reverse('base:delete_event', kwargs={'event_id': evento.pk}))
        self.assertEqual(resp.status_code, 200)
        resp2 = self.client.post(reverse('base:delete_event', kwargs={'event_id': evento.pk}))
        self.assertEqual(resp2.status_code, 302)
        num_elem_after = Event.objects.count()
        self.assertEqual(num_elem_after, num_elem_before - 1)

    def test_events_delete_clases(self):
        num_elem_before = Event.objects.count()
        evento = Event.objects.get(pk=1)
        resp = self.client.get(reverse('base:EventsDelete', kwargs={'pk': evento.pk}))
        self.assertEqual(resp.status_code, 200)
        resp2 = self.client.post(reverse('base:EventsDelete', kwargs={'pk': evento.pk}))
        self.assertEqual(resp2.status_code, 302)
        num_elem_after = NewsItem.objects.count()
        self.assertEqual(num_elem_after, num_elem_before - 1)

"""API"""


class ApiTest(TestCase):

    def setUp(self):
        publish_date = timezone.now()
        NewsItem.objects.create(title="lion", description="meow", publish_date=publish_date)
        start_date = timezone.now()
        end_date = timezone.now()
        Event.objects.create(title="cat", description="meow", start_date=start_date, end_date=end_date)

    def test_api_view_news(self):
        response = self.client.get(reverse("base:NewsItemListAPI"))
        self.assertEqual(response.status_code, 200)

    def test_api_view_events(self):
        response = self.client.get(reverse("base:EventsListAPI"))
        self.assertEqual(response.status_code, 200)

    def test_api_create_news(self):
        num_elem_before = NewsItem.objects.count()
        url = reverse("base:NewsItemListAPI")
        data = {"title": "kjbdfv", "description": "kjbfv", "publish_date": "2016-04-04"}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(NewsItem.objects.count(), num_elem_before + 1)

    def test_api_create_events(self):
        num_elem_before = Event.objects.count()
        url = reverse("base:EventsListAPI")
        data = {"title": "kjbdfv", "description": "kjbfv", "start_date": "2016-04-04", "end_date": "2016-04-04"}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Event.objects.count(), num_elem_before + 1)

    def test_api_update_news(self):
        client = APIClient()
        new = NewsItem.objects.get(title="lion")
        url = reverse("base:NewsItemDetailAPI", kwargs={"pk": new.pk})
        data = '{"title": "kjbdfv", "description": "kjbfv", "publish_date": "2016-05-05"}'
        response = self.client.patch(url, data, content_type='application/json', format='json')
        self.assertEqual(response.status_code, 200)

    def test_api_update_events(self):
        client = APIClient()
        evento = Event.objects.get(title="cat")
        url = reverse("base:EventsDetailAPI", kwargs={"pk": evento.pk})
        data = '{"title": "kjbdfv", "description": "kjbfv", "start_date": "2016-04-04", "end_date": "2016-04-04"}'
        response = self.client.patch(url, data, content_type='application/json', format='json')
        self.assertEqual(response.status_code, 200)

    def test_api_delete_new(self):
        num_elem_before = NewsItem.objects.count()
        new = NewsItem.objects.get(title="lion")
        url = reverse("base:NewsItemDetailAPI", kwargs={"pk": new.pk})
        response = self.client.delete(url, format='json')
        self.assertEqual(response.status_code, 204)
        self.assertEqual(NewsItem.objects.count(), num_elem_before - 1)

    def test_api_delete_event(self):
        num_elem_before = Event.objects.count()
        event = Event.objects.get(title="cat")
        url = reverse("base:EventsDetailAPI", kwargs={"pk": event.pk})
        response = self.client.delete(url, format='json')
        self.assertEqual(response.status_code, 204)
        self.assertEqual(Event.objects.count(), num_elem_before - 1)
