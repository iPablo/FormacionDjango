from django import forms
from .models import NewsItem, Event


class NewsForm(forms.ModelForm):

    class Meta:
        model = NewsItem
        fields = ('title', 'description')


class EventsForm(forms.ModelForm):

    class Meta:
        model = Event
        fields = ('title', 'description', 'start_date', 'end_date')
